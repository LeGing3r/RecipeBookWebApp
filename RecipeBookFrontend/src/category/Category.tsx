import { Recipe } from "../recipe/Recipe";

export type Category = {
    id: string;
    name: string;
    recipes: Recipe[];
}
export type CategoryWithoutRecipes = {
    id: string;
    name: string;
}

export const CategoryElement = (cats: Category[]) => {
    return <>
        
    </>
}