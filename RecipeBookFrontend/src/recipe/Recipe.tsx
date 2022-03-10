import { NutritionalInfo } from "./nutritionalinfo/NutritionalInfoModule"
import { localRecipesUrl, localRecipeUrl, recipeUrl } from "./RecipeModule"

export type Recipe = {
    name: string;
    chosen: boolean;
    cookingTime: CookingTime;
    nutritionalInfo: NutritionalInfo;
    id: string;
    instructions: string;
    ingredients: string[];
    categories: string[];
    portionSize: number;
}

export type RecipePageType = {
    elements: BareRecipe[],
    pagesAmount: number,
    currentPage: number,
    pageSize: number
}

export type BareRecipe = {
    name: string;
    chosen: boolean;
    id: string;
    categories: string[];
}

type CookingTime = {
    prepTime: number;
    actualCookingTime: number;
    totalCookingTime: number;
}

type RecipePageProps = {
    recipe: BareRecipe;
    chooseRecipe: (id: string) => void;
}

type RecipeDetailsProps = {
    recipe: Recipe;
}

export const RecipeElement = (props: RecipePageProps) => {
    let recipe = props.recipe;
    return <>
        <div className="r" style={{ display: "inline-block", fontSize: "2rem" }}>
            <a href={localRecipesUrl + "/" + recipe.id}>
                <img src={recipeUrl + "/image?id=" + recipe.id} alt={recipe.name} className="smllimg brdrd" />
            </a>
            <a href={localRecipesUrl + "/" + recipe.id}>{recipe.name}</a>
            <button className={recipe.chosen ? "mark chck" : "unmark chck"} onClick={() => props.chooseRecipe(recipe.id)} ></button>
        </div>
        <div className="separator"></div>
        <div className="right" id="recipe-cats">
            {recipe.categories.map(cat =>
                <a href={localRecipesUrl + "/category=" + cat} style={{ color: "black" }} >{cat}</a>
            )}
        </div>
    </>
}

export const IngredientCheckBox = (props: { id: string, addToList: (id: string) => void }) => {
    return (
        <label className="checkcontainer">
            <input type="checkbox" onChange={() => props.addToList(props.id)} />
            <span className="checkmark"></span>
        </label>
    )
}

export const RecipeFormIngredientElement = (props: { id: number, ingredient: string, updateIngredient: (id: number, toDelete?: boolean) => void }) => {
    return (
        <>
            <input type="text" className="ingBox" id={"ingredient" + props.id} value={props.ingredient} autoComplete="off"
                onChange={() => props.updateIngredient(props.id)} style={{ width: "70%" }} />
            <button name="removeIngredient" onClick={() => props.updateIngredient(props.id, true)}>
                <i className="material-icons">delete </i>
            </button>
        </>
    )
}

export const RecipeFormCategoryElement = (props: { category: string, index: number, updateCategory: (id: number, toDelete?: boolean) => void }) => {
    return (
        <>
            <input type="text" className="catBox" id={"category" + props.index} value={props.category} autoComplete="off"
                onChange={() => props.updateCategory(props.index)} />
            <button name="removeCat" onClick={() => props.updateCategory(props.index, true)}>
                <i className="material-icons">delete </i>
            </button>
        </>
    )
}

