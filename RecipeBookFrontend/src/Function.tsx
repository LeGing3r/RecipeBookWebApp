import React from "react";
import "./index.css";
import { BareRecipe } from "./recipe/RecipeModule";
export const toggleMenu = () => {

}

export const Navigation = () => {
  return (
    <nav>
      <div className="navbar">
        <div className="title">
          <a href="/recipes">Recipes 'R' Us</a>
          <button onClick={toggleMenu} className="menuButton">
            <i id="menuButtonIcon" className="material-icons" style={{ color: "white" }}>menu</i>
          </button>
        </div>
        <ul id="menu" className="menu">
          <li>
            <a href="/recipes">Recipes</a></li>
          <li>
            <a href="/categories">Categories</a>
          </li>
          <li>
            <a href="/recipe/new">Upload Recipe</a></li>
          <li>
            <a href="/shoppinglist">Shopping List</a></li>
        </ul>
        <div className="searchbar frtwth right">
          {//SEARCHBAR IMPLEMENTATION
          }
        </div>
      </div>
    </nav>
  );
}

export type PagesType = {
  size?: number;
  pageNumber?: number;
}
