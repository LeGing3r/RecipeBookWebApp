import { Item, ItemElement, SimilarItemsPopUp } from "./Item";
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

    function addNewItem(amount: string, name: string) {
        let item: Item = {
            name: name,
            measurement: amount,
            needed: true,
            publicId: ""
        };
        createNewItem({ item: item, updateList: updateList });
    }

    function updateItem(value: string, existing: Item) {
        updateExistingItem({ value: value, updateList: updateList }, existing);
        setSimilarItems([]);
    }

    async function getSimilarItems(evt: React.KeyboardEvent) {
        if (evt.key !== "Enter") {
            return;
        }
        var amount = document.getElementById("newTodoItemAmount")!.value
        var name = document.getElementById("newTodoItemName")!.value
        if (name === "") {
            return;
        }

        await checkForSimilarItems(name, setSimilarItems);
        console.log(similarItems)

        if (similarItems === undefined) {
            addNewItem(amount, name);
            document.getElementById("newTodoItemAmount")!.value = "";
            document.getElementById("newTodoItemName")!.value = "";
        } else if(similarItems.length === 1){
            let item: Item = {
                name: name,
                measurement: amount,
                publicId: similarItems[0].publicId,
                needed: true
            }
            updateExistingItem({ item: item, updateList: updateList }, item.publicId)
            setSimilarItems(undefined);
        }else{
            //final option
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
        let value = document.getElementById("newTodoItemName").value
        if (value === undefined) {
            return <></>
        }
        console.log(similarItems);
        return <SimilarItemsPopUp items={similarItems!} addNewItem={addNewItem} value={value} updateExistingItem={updateItem} />
    }

    if (!list) {
        refreshItems();
    }

    return (
        <div style={{ height: "90px" }}>
            <div>
                <div id="popup" style={{ display: "hidden" }}>
                    {
                        similarItems === undefined ? <></> :
                            createSimilarElementAttr
                    }
                </div>
                <div className="container">
                    <ul id="todoList" style={{ width: "100%", padding: "0" }}>
                        <li>
                            <label>
                                <input type="text" placeholder="Item Amount" auto-complete="off"
                                    className="todoItem" id="newTodoItemAmount"
                                    auto-focus="autofocus" onKeyDown={getSimilarItems} />
                                <input type="text" placeholder="Item Name" auto-complete="off"
                                    className="todoItem" id="newTodoItemName" onKeyDown={getSimilarItems} />
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