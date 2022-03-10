import { useState } from "react";
import { useLocation } from "react-router";
import { PageNumbersElement } from "../../page/PageModule";
import { CategoryElement, CategoryPage } from "./Category";
import { localCategoriesUrl } from "./CategoryModule";
import { getCategoryPage } from "./CategoryService";

export const CategoriesPage = () => {
    const [categoryPage, setPage] = useState<CategoryPage>({
        pageSize: 8,
        pagesAmount: 1,
        elements: [],
        currentPage: 1
    });
    const { search } = useLocation();
    const urlParams = new URLSearchParams(search);
    const size = urlParams.get('size');
    const page = urlParams.get('page');
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