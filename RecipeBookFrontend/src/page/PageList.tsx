type PageNumberElementProps = {
    totalPages: number;
    itemsPerPage: number;
    currentPage: number;
    url: string;
}

export const PageNumbersElement = (props: PageNumberElementProps) => {
    console.log(props)
    return (
        <div id="pageDirectory">
            {
                props.currentPage > 1 ? <a href={props.url + "/" + (props.currentPage - 1) + "/" + props.itemsPerPage}>{"<"}</a> : <></>}
            {
                [...Array(props.totalPages).keys()].map(i => <PageNumber number={i + 1} url={props.url} size={props.itemsPerPage} focused={props.currentPage === i + 1} />)
            }
            {
                props.currentPage < props.totalPages ? <a href={props.url + "?page=" + (props.currentPage + 1) + "&size=" + props.itemsPerPage}>{">"}</a> : <></>
            }
        </div>
    )
}

const PageNumber = (props: { number: number, url: string, size: number, focused: boolean }) => {
    return (
        <a href={props.url + "?page=" + props.number + "&size=" + props.size} className={props.focused ? "focused" : ""}>
            {props.number}
        </a>
    )
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

