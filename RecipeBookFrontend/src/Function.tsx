import React from "react";
import "./index.css";
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
            <a href="/recipes/new">Upload Recipe</a></li>
          <li>
            <a href="/todo">Shopping List</a></li>
        </ul>
        <div className="searchbar frtwth right">
          {//SEARCHBAR IMPLEMENTATION
          }
        </div>
      </div>
    </nav>
  );
}
