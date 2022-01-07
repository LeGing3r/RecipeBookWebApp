import { localRecipeUrl, Recipe, recipeUrl } from "./RecipeModule";
import * as React from "react";
import { IngredientCheckBox, RecipeElement, RecipePageElement } from "./Recipe";
import { Spinner } from "react-bootstrap";
import { alternateRecipeChosen, getChosenRecipes, getRecipe, getRecipePageList, getRecipes } from "./RecipeService";
import { useParams } from "react-router";

type PagesType = {
    size?: number;
    pageNumber?: number;
}

export const RecipesPage = (props: PagesType) => {
    const [recipes, updateRecipes] = React.useState<RecipePageElement[]>();
    const setTotal = (amount: number) => { totalRecipes = amount; }
    const chooseRecipe = (id: string) => {
        alternateRecipeChosen(id);
        getRecipes(actualSize, actualPageNumber, updateRecipes);
    }
    const alternateRecipesType = () => {
        let button = document.getElementById("recipesTypeButton");
        let title = document.getElementById("recipesTitle");
        if (button?.innerText === "Chosen") {
            getChosenRecipes(actualSize, actualPageNumber, updateRecipes);
            button.innerText = "All";
            title!.innerText = "Chosen Recipes";
        } else {
            getRecipes(actualSize, actualPageNumber, updateRecipes);
            button!.innerText = "Chosen";
            title!.innerText = "All recipes";
        }
    }
    let actualSize = props.size ? props.size : 8;
    let actualPageNumber = props.pageNumber ? props.pageNumber : 1;
    let totalRecipes = 0;
    if (!recipes) {
        getRecipes(actualSize, actualPageNumber, updateRecipes);
    }
    if (totalRecipes === 0) {
        getRecipePageList(setTotal);
    }
    return (
        <div className="container">
            <div className="cntr" style={{ width: "fit-content, -moz-fit-content" }}>
                <button type="submit" className="scaling-button" id="recipesTypeButton" onClick={() => alternateRecipesType()}>Chosen</button>
            </div>
            <h1 id="recipesTitle">All Recipes</h1>
            <div className="separator" />
            {
                recipes === undefined ? <Spinner animation={"border"} role="status"><span className="visually-hidden">Loading...</span></Spinner> :
                    recipes.map(recipe => <RecipeElement recipe={recipe} chooseRecipe={chooseRecipe} />)
            }
            <div className="separator" />
            {/* <PageNumbersElement totalItems={totalRecipes} itemsPerPage={actualSize} currentPage={actualPageNumber} /> */}
        </div>
    );



}

export const RecipePage = () => {
    const { id } = useParams();
    const [recipe, setRecipe] = React.useState<Recipe>();
    let neededList: string[] = [];
    const updateNeededList = (name: string) => {
        if (neededList.includes(name)) {
            neededList = neededList.filter(obj => obj !== name);
        }
        else {
            neededList.push(name);
        }
        console.log(neededList)
    }
    const chooseRecipe = (id: string) => {
        console.log(recipe);
        alternateRecipeChosen(id);
        getRecipe(id!, setRecipe);
    }
    if (recipe === undefined) {
        getRecipe(id!, setRecipe);
        if (recipe === undefined) {
            return <>RECIPE NOT FOUND</>

        }
    }
    return <>
        {recipe === undefined ?
            <Spinner animation={"border"} role="status"><span className="visually-hidden">Loading...</span></Spinner> : <>
                <div className="container">
                    <div className="row">
                        <h2 style={{ textAlign: "center", fontSize: "2.5rem" }} className="lnblock">{recipe.name}</h2>
                        <a href={localRecipeUrl + "?recipeId=" + id + "/edit"} className="lnblock"><i className="material-icons">edit</i></a>
                        <button className={recipe.chosen ? "mark chck" : "unmark chck"} onClick={() => chooseRecipe(recipe.id)} ></button>
                    </div>
                    <div className="separator"></div>
                    <div>
                        <img src="@{${recipe.imgLoc}}" alt={recipe.name} className="lrgimg brdrd" style={{ margin: "20px auto" }} />
                        {/* SCALING RECIPE */}
                        <h3 style={{ width: "33%", float: "left" }}>{"Prep time:" + recipe.cookingTime.prepTime}</h3>
                        <h3 style={{ width: "33%", float: "left" }}>{"Cook time:" + recipe.cookingTime.actualCookingTime}</h3>
                        <h3 style={{ width: "33%", float: "left" }}>{"Total time:" + recipe.cookingTime.totalCookingTime}</h3>
                    </div>
                    <div>
                        <table className="recipeTable flwth">
                            <thead>
                                <tr>
                                    <th className="iH" style={{ width: "80%", borderBottom: "black solid 1px", fontSize: "2rem" }}>Ingredients</th>
                                    <th style={{ borderBottom: "black solid 1px", fontSize: "2rem" }}>Need It</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr style={{ marginBottom: "3em" }}>
                                    <h3>Portions: {recipe.portionSize}</h3>
                                    <button>X2</button>
                                    <button>X3</button>
                                    <button>X4</button>
                                </tr>
                                <tr><div className="separator" /></tr>
                                {recipe.ingredients.length === 0 ? "HELLO" : recipe.ingredients.map((ingredient) => {
                                    return (<tr>
                                        <td>
                                            <p>{ingredient}</p>
                                        </td>
                                        <td style={{ textAlign: "center" }}>
                                            <IngredientCheckBox id={ingredient} addToList={updateNeededList} />
                                        </td>
                                    </tr>
                                    )
                                })}
                                <tr><form action=""></form></tr>
                            </tbody>
                        </table>
                        <div className="flwth">
                            <div className="separator" style={{ marginTop: "2rem" }}></div>
                            <h1 style={{ textAlign: "center", margin: "0 0 1rem 0" }}>Instructions</h1>
                            {/* UPDATE INSTRUCTIONS TO A LIST */}
                            <h2>{recipe.instructions}</h2>
                        </div>
                    </div>

                    <div style={{ margin: "5rem 0" }}>
                        <h3>Categories:</h3>
                        <div className="separator"></div>
                        <div className="flwth">
                            {recipe.categories.map(category => {
                                <>
                                    <h4 style={{ display: "inline" }}>
                                        <a href={"http://localhost:2017/categories?categoryId=" + category.id}>{category.name}</a>
                                    </h4>
                                </>
                            })}
                        </div>
                        <form action={recipeUrl + "/delete?recipeId=" + id} method="post"
                            style={{ position: "absolute", bottom: "-1rem", right: "0" }}>
                            <button type="submit" onClick={() => { if (window.confirm('Are you sure you want to delete this recipe??')) { } }}><i
                                className="material-icons scaling-button">delete</i></button>
                        </form>
                    </div>
                </div>
            </>
        }
    </>
}

export const RecipeForm = () => {
    return (
        <div style={{ height: "90px" }}>
        </div>
    );
}

export const RecipeEdit = () => {
    return (
        <>
        </>
    )
}