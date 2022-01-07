import { useEffect } from "react"
import { Category, categoryUrl, CategoryWithoutRecipes } from "../category/CategoryModule"
import { localRecipesUrl, localRecipeUrl, recipeUrl } from "./RecipeModule"

export type Recipe = {
    name: string;
    chosen: boolean;
    cookingTime: CookingTime;
    nutritionalInfo: NutritionalInfo;
    id: string;
    instructions: string;
    ingredients: string[];
    categories: Category[];
    portionSize: number;
}

export type RecipePageElement = {
    name: string;
    chosen: boolean;
    id: string;
    categories: CategoryWithoutRecipes[];
}

type CookingTime = {
    prepTime: number;
    actualCookingTime: number;
    totalCookingTime: number;
}

type NutritionalInfo = {
    uri: string;
    calories: number;
    totalWeight: number;
    dietLabels: string[];
    healthLabels: string[];
    cautions: string[];
}

type RecipePageProps = {
    recipe: RecipePageElement;
    chooseRecipe: (id: string) => void;
}

type RecipeDetailsProps = {
    recipe: Recipe;
}

export const RecipeElement = (props: RecipePageProps) => {
    useEffect(() => console.log(props.recipe.chosen), []);
    let recipe = props.recipe;
    return <>
        <div className="r" style={{ display: "inline-block", fontSize: "2rem" }}>
            <a href={localRecipesUrl + recipe.id}>
                <img src={recipeUrl + "/image?id=" + recipe.id} alt={recipe.name} className="smllimg brdrd" />
            </a>
            <a href={localRecipesUrl + recipe.id}>{recipe.name}</a>
            <button className={recipe.chosen ? "mark chck" : "unmark chck"} onClick={() => props.chooseRecipe(recipe.id)} ></button>
        </div>
        <div className="separator"></div>
        <div className="right" id="recipe-cats">
            {recipe.categories.map(cat => <a href={categoryUrl + cat.id}
                style={{ color: "black" }} >{cat.name}</a>)}

        </div>
    </>
}

export const IngredientCheckBox = (props: {id: string, addToList: (id:string) => void}) => {
    return (
        <label className="checkcontainer">
            <input type="checkbox" onChange={() => props.addToList(props.id)}/>
            <span className="checkmark"></span>
        </label>
    )
}