import { recipesUrl } from "../recipe/RecipeModule";
import React from "react";

type PageNumberElementProps = {
    totalItems: number;
    itemsPerPage: number;
    currentPage: number;
}

export const PageNumbersElement = ({ totalItems, itemsPerPage, currentPage }: PageNumberElementProps) => {
    return <div style={{ font: "1.3rem", marginTop: "50px", marginLeft: "50px" }}>
        <a href={recipesUrl + '?size=' + itemsPerPage + '&currentPage=' + currentPage}></a>
    </div>
}

//     <div style={{font: "1.3rem" margin-top: 50px; margin-left: 50p{x;"}}>
//     <a if="${currentPage gt 1}"
//         href="@{__${link == '/recipes' ? '/recipes' : '/chosen'}__(size=${recipePage.size}, page=__${currentPage}__-1)}"
//         style="position: relative"><i
//             className="material-icons" style="position: absolute; left: -25px; top: 1px;">keyboard_arrow_left</i></a>
//     <ul className="pages" style="display: inline; padding: 0;">
//         <li each="pageNumber : ${pageNumbers}" style="display: inline;">
//             <a href="@{__${link == '/recipes' ? '/recipes' : '/chosen'}__(size=${recipePage.size}, page=${pageNumber})}"
//                 text=${pageNumber}
//                 className="${pageNumber==recipePage.number + 1} ? active" className="pagenumber"></a></li>
//     </ul>
//     <a if="${currentPage lt recipePage.totalPages}"
//         href="@{__${link == '/recipes' ? '/recipes' : '/chosen'}__(size=${recipePage.size}, page=${currentPage} + 1)}"
//         style="position: relative;"><i
//             className="material-icons" style="position: absolute; right: -25px; top: 1px;">keyboard_arrow_right</i></a>

// </div>

