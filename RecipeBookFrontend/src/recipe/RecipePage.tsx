import { useState } from "react";
import { Spinner } from "react-bootstrap";
import { useParams } from "react-router-dom";
import { NutritionalInfoElement } from "./nutritionalinfo/NutritionalInfo";
import { IngredientCheckBox, localRecipesUrl, localRecipeUrl, Recipe, recipeUrl } from "./RecipeModule";
import { alternateRecipeChosen, getRecipe } from "./RecipeService";

export const RecipePage = () => {
    const { id } = useParams();
    const [recipe, setRecipe] = useState<Recipe>();
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
        console.log(recipe);
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
                                    <h3 style={{ display: "inline-block" }}>Portions: {recipe.portionSize}</h3>
                                    <div style={{ display: "inline-block", marginLeft: "0.5em" }}>
                                        <button>X2</button>
                                        <button>X3</button>
                                        <button>X4</button>
                                    </div>
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
                                    </tr>)
                                })}
                                <tr><td> </td>
                                    <td><button>Add to Shopping List</button></td>
                                </tr>
                            </tbody>
                        </table>
                        <div className="flwth">
                            <div className="separator" style={{ marginTop: "2rem" }}></div>
                            <h1 style={{ textAlign: "center", margin: "0 0 1rem 0" }}>Instructions</h1>
                            <h2>{recipe.instructions}</h2>
                        </div>
                    </div>
                    <div>
                        <h3 style={{ display: "inline-block" }}>Categories:</h3>
                        {recipe.categories.map(cat =>
                            <button style={{ display: "inline-block", marginLeft: "1em" }}><a href={localRecipesUrl + "/category=" + cat}>{cat}</a></button>
                        )}
                    </div>
                    <div className="separator"></div>
                    <div>
                        <NutritionalInfoElement itemName={recipe.name} nutritionalInfo={recipe.nutritionalInfo} />
                    </div>
                    <form action={recipeUrl + "/delete?recipeId=" + id} method="post"
                        style={{ position: "absolute", bottom: "-1rem", right: "0" }}>
                        <button type="submit" onClick={() => { if (window.confirm('Are you sure you want to delete this recipe??')) { } }}><i
                            className="material-icons scaling-button">delete</i></button>
                    </form>
            </div>
            </>
}
    </>
}