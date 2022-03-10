import { useState } from "react";
import { useNavigate, useParams } from "react-router";
import { Recipe, RecipeFormIngredientElement, RecipeFormCategoryElement, localRecipeUrl, recipeUrl, updateRecipe, updateRecipeImage, getNewRecipe, getRecipe } from "./RecipeModule";
import { } from "./RecipeService";

export const RecipeForm = () => {
    const [image, updateImage] = useState<string>('');
    const [recipe, setRecipe] = useState<Recipe>();
    const [file, setFile] = useState<File>();
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
        if (recipe!.categories === undefined) {
            return;
        }
        let tempCats = recipe!.categories;
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