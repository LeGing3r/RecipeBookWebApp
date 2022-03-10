import { localRecipesUrl } from "./RecipeModule";
import * as React from "react";
import { RecipeElement, RecipePageType } from "./Recipe";
import * as RecipeService from "./RecipeService";
import { useNavigate, useParams } from "react-router";
import { PageNumbersElement } from "../page/PageModule";
import { useLocation } from "react-router";

export const RecipesPage = () => {
    const { search } = useLocation();
    const urlParams = new URLSearchParams(search);
    const size = urlParams.get('size');
    const page = urlParams.get('page');
    const chosen = urlParams.get('chosen');
    const { category } = useParams();
    const navigate = useNavigate();
    let actualSize = size ? Number(size) : 8;
    let actualPageNumber = page ? Number(page) : 1;
    const [recipePage, setPage] = React.useState<RecipePageType>();
    const chooseRecipe = (id: string) => {
        RecipeService.alternateRecipeElementChosen(id, { page: actualSize, size: actualPageNumber, setPage: setPage }, chosen === null);
        setPage(undefined);
    }
    if (recipePage === undefined) {
        if (category !== undefined) {
            RecipeService.getRecipesWithCategory(setPage, category)
        } else {
            RecipeService.getRecipes({ page: actualPageNumber, size: actualSize, setPage: setPage }, chosen === null);
        }
    }
    return (
        recipePage === undefined ? <></> :
            <div className="container">
                <div className="cntr" style={{ width: "fit-content, -moz-fit-content" }}>
                    {chosen === null ?
                        <a style={{ background: "black", borderRadius: "0.5em", padding: "0.3em" }} href={localRecipesUrl + "/chosen"}>Chosen</a> :
                        <a style={{ background: "black", borderRadius: "0.5em", padding: "0.3em" }} href={localRecipesUrl}>All Recipes</a>
                    }
                </div>
                <h1 id="recipesTitle">
                    {chosen ? "Chosen Recipes" : "All Recipes"}
                </h1>
                <div className="separator" />
                {
                    recipePage!.elements === undefined ? <></> :
                        recipePage!.elements.map(recipe => <RecipeElement recipe={recipe} chooseRecipe={chooseRecipe} />)
                }
                <div className="separator" />
                {
                    recipePage.elements ?
                        <div className="pageDirectory">
                            <PageNumbersElement itemsPerPage={actualSize} currentPage={actualPageNumber} totalPages={recipePage.pagesAmount} url={localRecipesUrl} />
                        </div> : <></>
                }
            </div >
    );
}