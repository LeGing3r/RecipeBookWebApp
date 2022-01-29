import { localRecipesUrl } from "../RecipeModule"


export type CategoryPage = {
    elements: string[],
    pagesAmount: number,
    currentPage: number,
    pageSize: number
}
export const CategoryElement = (props: {name: string}) => {
    return (
        <div className="card frtwth">
        <div className="card-content">
            <div className="card-contents">
                <div className="card-title">
                    <a className="name" href={localRecipesUrl + "/category=" + props.name}>{props.name}</a>
                </div>
            </div>
        </div>
    </div>
    )
}