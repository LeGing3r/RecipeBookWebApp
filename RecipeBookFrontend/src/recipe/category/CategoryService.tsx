import axios from "axios";
import { CategoryPage } from "./Category";
import { categoriesUrl } from "./CategoryModule";

type PageRequestParams = {
    size: number;
    pageNumber: number;
    updatePage: (page: CategoryPage) => void;
}

export function getCategoryPage(props: PageRequestParams) {
    axios.get<CategoryPage>(categoriesUrl + "?page=" + props.pageNumber + "&size=" + props.size)
        .then(res => {
            props.updatePage(res.data);
            });
}