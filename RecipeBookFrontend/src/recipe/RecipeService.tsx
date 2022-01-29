import axios from "axios";
import { RecipePageType } from "./Recipe";
import { Recipe, recipesUrl, recipeUrl } from "./RecipeModule";

type PageRequestParams = {
    page: number;
    size: number;
    setPage: (page: RecipePageType) => void;
}

export function getRecipes(requestParams: PageRequestParams, allRecipes: boolean) {
    if (allRecipes) {
        console.log(allRecipes)
        axios.get(recipesUrl + "?page=" + requestParams.page + "&size=" + requestParams.size)
            .then(res => {
                requestParams.setPage(res.data)
            });
    } else {
        axios.get(recipesUrl + "/chosen?page=" + requestParams.page + "&size=" + requestParams.size)
            .then(res => {
                requestParams.setPage(res.data)
            });
    }
}

export function getRecipesWithCategory(updatePage: (page: RecipePageType) => void, category: string) {
    axios.get(recipesUrl + "/search?query=" + category + "&searchType=" + "CATEGORY")
        .then(res => updatePage(res.data));
}

export function getRecipe(id: string, updateRecipe: (recipe: Recipe) => void) {
    axios.get<Recipe>(recipeUrl + "?id=" + id)
        .then(res => {
            updateRecipe(res.data)
        });
}

export function getRecipePageList(setTotal: (amount: number) => void) {
    axios.get<number>(recipesUrl + "/total")
        .then(res => setTotal(res.data));
}

export function alternateRecipeElementChosen(id: string, requestParams: PageRequestParams, chosen: boolean) {
    axios.put(recipeUrl + "/choose?id=" + id);
}

export function alternateRecipeChosen(id: string, updateRecipe: (recipe: Recipe) => void) {
    axios.put(recipeUrl + "/choose?id=" + id)
        .finally(() => getRecipe(id, updateRecipe));
}

export function getNewRecipe(updateRecipe: (recipe: Recipe) => void) {
    axios.get(recipeUrl + "/new")
        .then(res => {
            updateRecipe(res.data);
        })
}

export function submitNewRecipe(recipe: Recipe, added: boolean) {
    axios.post(recipeUrl + "/add", recipe)
        .then(res => added = true)
        .catch(e => added = false);
}

export function updateRecipe(recipe: Recipe, updateRecipe: (recipe: Recipe) => void) {
    axios.put<Recipe>(recipeUrl + "?id=" + recipe.id, recipe)
        .then(res => updateRecipe(res.data));
}

export function updateRecipeImage(id: string, file: File) {
    var formData = new FormData();
    formData.append("image", file);
    axios.post(recipeUrl + "/image?id=" + id, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
}