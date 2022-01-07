import axios from "axios";
import { RecipePageElement } from "./Recipe";
import { Recipe, recipesUrl, recipeUrl } from "./RecipeModule";

export function getRecipes(size: number, amount: number, updateList: (recipes: RecipePageElement[]) => void) {
    axios.get<Recipe[]>(recipesUrl + "?size=" + size + "&amount=" + amount)
        .then(res => {
            updateList(res.data.recipes)
        });
}

export function getChosenRecipes(size: number, amount: number, udpateList: (recipes: RecipePageElement[]) => void) {
    axios.get<Recipe[]>(recipesUrl + "/chosen?size=" + size + "&amount=" + amount)
        .then(res => {
            udpateList(res.data.recipes)
        });
}

export function getRecipe(id: string, updateRecipe: (recipe: Recipe) => void) {
    axios.get<Recipe>(recipeUrl + "?recipeId=" + id)
        .then(res => {
            console.log(res.data);
            updateRecipe(res.data)
        });
}

export function getRecipePageList(setTotal: (amount: number) => void) {
    axios.get<number>(recipesUrl + "/total")
        .then(res => setTotal(res.data));
}

export function alternateRecipeChosen(id: string) {
    axios.put(recipeUrl + "/choose?recipeId=" + id);
}