import { useState } from "react";
import { useParams } from "react-router";
import { PagesType } from "../../Function";
import { PageNumbersElement } from "../../page/PageModule";
import { CategoryElement, CategoryPage } from "./Category";
import { localCategoriesUrl } from "./CategoryModule";
import { getCategoryPage } from "./CategoryService";

export const CategoriesPage = (props: PagesType) => {
    const [categoryPage, setPage] = useState<CategoryPage>({
        pageSize: 8,
        pagesAmount: 1,
        elements: [],
        currentPage: 1
    });
    const { page, size } = useParams();
    let actualPage = page ? Number(page) : 1;
    let actualSize = size ? Number(size) : 20;
    if (categoryPage.elements.length === 0) {
        getCategoryPage({ size: actualSize, pageNumber: actualPage, updatePage: setPage })
    }
    return (
        <div className="container">
            <div className="categories">
                {categoryPage.elements.map(cat => <CategoryElement name={cat} />)}
            </div>
            <PageNumbersElement url={localCategoriesUrl} totalPages={categoryPage.pagesAmount} itemsPerPage={categoryPage.pageSize} currentPage={categoryPage.currentPage} />
        </div>
    )
}