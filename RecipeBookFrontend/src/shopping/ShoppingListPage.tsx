import { Item, ItemElement, SimilarItemsElement } from "./Item";
import React, { useState } from "react";
import "../index.css";
import * as itemService from "./ItemService";
import { checkForSimilarItems, createNewItem, getItems, updateExistingItem, updateItems } from "./ItemService";


export const ShoppingList = () => {
    const [list, updateList] = useState<Item[]>();
    const [similarItems, setSimilarItems] = useState<Item[]>();

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
        getItems(updateList);
    }

    function addNewItem(value?: string) {
        let newValue = value === undefined ? document.getElementById("newTodoItem").value : value
        createNewItem({ value: newValue, updateList: updateList });
    }

    function updateItem(value: string, existing: Item) {
        updateExistingItem({ value: value, updateList: updateList }, existing);
        setSimilarItems([]);
    }

    function getSimilarItems(evt: React.KeyboardEvent) {
        if (evt.key !== "Enter") {
            return;
        }
        var value = document.getElementById("newTodoItem")?.value
        checkForSimilarItems({ value: value, updateList: setSimilarItems });
        if (similarItems === undefined) {
            console.log(similarItems)
            addNewItem(value);
        }
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

    function createSimilarElementAttr() {
        let value = document.getElementById("newTodoItem").value
        if (value === undefined) {
            return <></>
        }
        return <SimilarItemsElement items={similarItems!} addNewItem={addNewItem} value={value} updateExistingItem={updateItem} />
    }

    if (!list) {
        refreshItems();
    }

    return (
        <div style={{ height: "90px" }}>
            <div>
                <div id="popup" style={{display: "hidden"}}>
                    {
                        similarItems === undefined ? <></> :
                            createSimilarElementAttr
                    }
                </div>
                <div className="container">
                    <ul id="todoList" style={{ width: "100%", padding: "0" }}>
                        <li>
                            <label>
                                <input type="text" placeholder="New Item" auto-complete="off"
                                    className="hlfwth todoItem" id="newTodoItem"
                                    auto-focus="autofocus" onKeyDown={getSimilarItems} />
                            </label>
                        </li>
                        {list === undefined ? <></> :
                            list.map((item: Item, i) =>
                                <ItemElement item={item} index={i} updateNeeded={updateNeeded} updateItemString={updateItemString} />)

                        }
                    </ul>
                    <button className="scaling-button" type="submit" onClick={udpateItems}>Refresh</button>
                </div>
            </div>
        </div>
    );
}