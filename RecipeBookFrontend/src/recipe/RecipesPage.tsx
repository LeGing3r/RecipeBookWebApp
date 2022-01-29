import { localRecipesUrl, localRecipeUrl, Recipe, recipeUrl } from "./RecipeModule";
import * as React from "react";
import { IngredientCheckBox, RecipeElement, RecipeFormCategoryElement, RecipeFormIngredientElement, RecipePageType } from "./Recipe";
import { Spinner } from "react-bootstrap";
import { alternateRecipeChosen, alternateRecipeElementChosen, getNewRecipe, getRecipe, getRecipes, getRecipesWithCategory, updateRecipe, updateRecipeImage } from "./RecipeService";
import { useNavigate, useParams } from "react-router";
import { PageNumbersElement } from "../page/PageModule";

export const RecipesPage = (props: { chosen?: boolean }) => {
    const { pageNumber, size, category } = useParams();
    const navigate = useNavigate();
    let actualSize = size ? Number(size) : 8;
    let actualPageNumber = pageNumber ? Number(pageNumber) : 1;
    const [recipePage, setPage] = React.useState<RecipePageType>();
    const chooseRecipe = (id: string) => {
        alternateRecipeElementChosen(id, { page: actualSize, size: actualPageNumber, setPage: setPage }, props.chosen === undefined);
        setPage(undefined);
    }
    if (recipePage === undefined) {
        if (category !== undefined) {
            getRecipesWithCategory(setPage, category)
        } else {
            getRecipes({ page: actualPageNumber, size: actualSize, setPage: setPage }, props.chosen === undefined);
        }
    }
    return (
        recipePage === undefined ? <></> :
            <div className="container">
                <div className="cntr" style={{ width: "fit-content, -moz-fit-content" }}>
                    {
                        props.chosen === undefined ?
                            <a style={{ background: "black", borderRadius: "0.5em", padding: "0.3em" }} href={localRecipesUrl + "/chosen"}>Chosen</a> :
                            <a style={{ background: "black", borderRadius: "0.5em", padding: "0.3em" }} href={localRecipesUrl}>All Recipes</a>
                    }
                </div>
                <h1 id="recipesTitle">
                    {
                        props.chosen ? "Chosen Recipes" : "All Recipes"
                    }
                </h1>
                <div className="separator" />
                {
                    recipePage!.elements === undefined ? <></> :
                        recipePage!.elements.map(recipe => <RecipeElement recipe={recipe} chooseRecipe={chooseRecipe} />)
                }
                <div className="separator" />
                {
                    recipePage.elements ? <></> :
                        <div className="pageDirectory">
                            <PageNumbersElement itemsPerPage={actualSize} currentPage={actualPageNumber} totalPages={recipePage.pagesAmount} url={localRecipesUrl} />
                        </div>
                }
            </div >
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
    }
    const chooseRecipe = (id: string) => {
        alternateRecipeChosen(id, setRecipe);
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
                        <a href={localRecipeUrl + "/" + id + "/edit"} className="lnblock"><i className="material-icons">edit</i></a>
                        <button className={recipe.chosen ? "mark chck" : "unmark chck"} onClick={() => chooseRecipe(recipe.id)} ></button>
                    </div>
                    <div className="separator"></div>
                    <div>
                        <img src={recipeUrl + "/image?id=" + id} alt={recipe.name} className="lrgimg brdrd" style={{ margin: "20px auto" }} />
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
                                <h4 style={{ display: "inline" }}>
                                    <a href={localRecipesUrl + "?category=" + category}>{category}</a>
                                </h4>
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
    const [image, updateImage] = React.useState<string>('');
    const [recipe, setRecipe] = React.useState<Recipe>();
    const [file, setFile] = React.useState<File>();
    const navigate = useNavigate();
    const { id } = useParams();

    function updateIngredient(index: number, toDelete?: boolean) {
        if (recipe?.ingredients === undefined) {
            return;
        }
        let tempIngredients = recipe.ingredients;
        if (toDelete) {
            tempIngredients?.splice(index, 1);
        } else {
            let ingredientInput: HTMLInputElement = document.getElementById("ingredient" + index);
            let newVal = ingredientInput.value;
            if (newVal === undefined) {
                return;
            }
            tempIngredients[index] = newVal;
        }
        setRecipe(prevState => ({ ...prevState, ingredients: tempIngredients }))
    }
    function addNewIngredient(event?: React.KeyboardEvent) {
        if (event !== undefined && event.key !== "Enter") {
            return;
        }
        let inputBox: HTMLInputElement = document.getElementById("addIngredient")
        if (!inputBox) {
            return;
        }
        let tempIng = recipe?.ingredients
        if (tempIng === undefined) {
            return;
        }
        tempIng.push(inputBox.value);
        setRecipe(prevState => ({ ...prevState, ingredients: tempIng }));
        inputBox.value = "";
    }
    function updateCategory(index: number, toDelete?: boolean) {
        if (recipe.categories === undefined) {
            return;
        }
        let tempCats = recipe.categories;
        if (toDelete) {
            tempCats?.splice(index, 1);
        } else {
            let newVal = document.getElementById("category" + index).value;
            if (newVal === undefined) {
                return;
            }
            tempCats[index].name = newVal;
        }

        setRecipe(prevState => ({ ...prevState, categories: tempCats }));

    }
    function addNewCategory(event?: React.KeyboardEvent) {
        if (event !== undefined && event.key !== "Enter") {
            return;
        }
        let inputBox: HTMLInputElement = document.getElementById("addCategory")
        let tempCats = recipe?.categories
        if (tempCats === undefined) {
            return;
        }
        let newCat = inputBox!.value
        tempCats.push(newCat);
        setRecipe(prevState => ({ ...prevState, categories: tempCats }));
        inputBox!.value = "";
    }
    function onSubmit(evt: React.FormEvent<HTMLButtonElement>) {
        if (recipe === undefined) {
            return;
        }
        updateRecipe(recipe, setRecipe)
        if (image !== "") {
            updateRecipeImage(recipe?.id, file);
        }
        navigate(localRecipeUrl + recipe.id);
    }
    function setImage(evt: React.ChangeEvent<HTMLInputElement>) {
        const imageString = URL.createObjectURL(evt.currentTarget.files[0]);
        updateImage(prevState => (imageString));
        setFile(evt.currentTarget.files[0]);
    }
    function updateInstructions(evt: React.ChangeEventHandler<HTMLTextAreaElement>) {
        setRecipe(prevState => ({ ...prevState, instructions: evt.target.value }))
    }

    if (recipe === undefined) {
        if (id === undefined) {
            getNewRecipe(setRecipe);
        }
        else {
            getRecipe(id, setRecipe);
        }
    }
    return (
        recipe === undefined ? <></> :
            <div className="rcpfrm container">
                <div className="cntr" id="recipeTitle" style={{ textAlign: "center" }}>
                    <input type="text" autoComplete="off" placeholder="Name of Recipe" style={{ textAlign: "center" }} />
                    <div className="separator" />
                    <br />
                    {id !== undefined ? <></> :
                        <button style={{ position: "absolute", right: 0, top: 0 }}>Copy From Site</button>
                    }
                </div>
                <div id="imagePreview">
                    {
                        image === "" && id !== undefined ?
                            <img src={recipeUrl + "/image?id=" + id} id="recipeImg" className="medimg cntr" /> :
                            <img id="recipeImg" className="medimg cntr" src={image} />
                    }
                </div>
                <br />
                <div id="imageInput" style={{ border: "solid gray 1px", borderRadius: "1em", width: "fit-content", background: "white" }} className="cntr">
                    <input type="file" id="file" name="file" accept="image/*" onChange={setImage}
                        style={{ padding: "2rem 0" }} />
                    <label htmlFor="file" style={{ padding: "2rem" }}>Upload Picture</label>
                </div>
                <br />
                <div id="ingredients" className="cntr">
                    {
                        recipe?.ingredients.map((ingredient, i) =>
                            <RecipeFormIngredientElement id={i} ingredient={ingredient} updateIngredient={updateIngredient} />)
                    }
                    <div>
                        <input id="addIngredient" type="text" className="newIngBox" placeholder="New Ingredient" autoComplete="off" onKeyDown={(evt) => addNewIngredient(evt)} />
                        <button name="addIngredient" className="addIngredientBtn" onClick={() => addNewIngredient()}>
                            <i className="material-icons">add</i>
                        </button>
                    </div>
                    <br /><br />
                </div >
                <div id="instructions">
                    <textarea id="instructionsBox" cols={30} rows={5} placeholder="Enter instructions here..." className="nntywth" value={recipe.instructions === undefined ? "" : recipe.instructions} onChange={updateInstructions} />
                </div>
                <div id="categories" className="lnblock">
                    <div className="flwth cntr">
                        <h3>Add Categories: </h3>
                        {
                            recipe?.categories.map((cat, i) => <RecipeFormCategoryElement category={cat} index={i} updateCategory={updateCategory} />)
                        }
                        <div>
                            <input type="text" id="addCategory" className="newCatBox" placeholder="Category Name" autoComplete="off" style={{ width: "70%" }} onKeyDown={(evt) => addNewCategory(evt)} />
                            <button type="submit" ><i className="material-icons">add</i></button>
                        </div>
                    </div >
                </div >
                <button style={{ position: "absolute", right: "0", bottom: "0" }} onClick={onSubmit}>Save Recipe</button>
            </div >

    )
}