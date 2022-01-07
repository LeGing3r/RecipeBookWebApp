import { Item, ItemElement } from "./Item";
import React, { useState } from "react";
import "../index.css";
import * as itemService from "./ItemService";


export const ShoppingList = () => {
    const [list, updateList] = useState<Item[]>();

    function updateNeeded(item: Item) {
        if (!list) {
            return;
        }
        const newList = list.map((item1) => {
            if (item.publicId === item1.publicId) {
                const updatedItem: Item = {
                    ...item1,
                    needed: !item1.needed,
                }
                return updatedItem;
            }
            return item1;
        });
        updateList(newList);
    }

    function refreshItems() {
        itemService.getItems(updateList);
    }

    function addNewItem(item: React.FormEvent<HTMLFormElement>) {
        item && item.preventDefault();
        var value = item.currentTarget.getElementsByClassName("todoItem")[0].value
        itemService.addNewItem(value);
        refreshItems();
    }

    function udpateItems() {
        itemService.updateItems(list!, updateList);
    }

    function updateItemString(item: Item, evt: React.FocusEvent<HTMLInputElement, Element>) {
        if (!list) {
            return;
        }
        const newList = list.map((item1) => {
            if (item.publicId === item1.publicId) {
                const updatedItem: Item = {
                    ...item1,
                    stringValue: evt.target.value,
                }
                return updatedItem;
            }
            return item1;
        });

        updateList(newList);

    }

    if (!list) {
        refreshItems();
        return <div />
    }

    return (
        <div style={{ height: "90px" }}>
            <div>
                <div className="container">
                    <ul id="todoList" style={{ width: "100%", padding: "0" }}>
                        <li>
                            <form style={{ margin: "2rem 0" }} onSubmit={(evt) => addNewItem(evt)}>
                                <label>
                                    <input type="text" placeholder="New Item" auto-complete="off"
                                        className="hlfwth todoItem"
                                        auto-focus="autofocus" />
                                </label>
                            </form>
                        </li>
                        {list.map((item: Item, i) =>
                            <ItemElement item={item} index={i} updateNeeded={updateNeeded} updateItemString={updateItemString} />)}
                    </ul>
                    <button className="scaling-button" type="submit" onClick={udpateItems}>Refresh</button>
                </div>
            </div>
        </div>
    );
}