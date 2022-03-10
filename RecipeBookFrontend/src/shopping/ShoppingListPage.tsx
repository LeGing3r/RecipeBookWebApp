import { Item, ItemElement, SimilarItemsPopUp } from "./Item";
import React, { useState } from "react";
import "../index.css";
import * as itemService from "./ItemService";
import { checkForSimilarItems, createNewItem, getItems, updateExistingItem, updateItems } from "./ItemService";
import { VALID_UNITS } from "./ShoppingModule";


export const ShoppingList = () => {
    const [list, updateList] = useState<Item[]>();
    const [similarItems, setSimilarItems] = useState<Item[]>();
    const [localError, setLocalError] = useState<string>();

    function updateNeeded(item: Item) {
        if (!list) {
            return;
        }
        const newList = list.map((item1) => {
            if (item.id === item1.id) {
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
            id: ""
        };
        createNewItem({ item: item, updateList: updateList });
    }

    function updateItem(value: string, existing: Item) {
        updateExistingItem({ value: value, updateList: updateList }, existing);
        setSimilarItems([]);
    }

    async function getSimilarItems(evt: React.KeyboardEvent) {
        if (evt.key !== "Enter" || localError) {
            return;
        }
        var amount = document.getElementById("newTodoItemAmount")
        var amountValue: string = amount?.value.replace(" ", "");
        var name = document.getElementById("newTodoItemName")
        var nameValue = name?.value;
        if (nameValue === "") {
            setLocalError("No name for ingredient added, please fix.")
            return;
        }

        await checkForSimilarItems(nameValue, setSimilarItems);

        if (similarItems === undefined || similarItems.length <= 1) {
            if (amountValue === "") {
                amountValue = "1";
            }
            let measurement = amountValue.match("([0-9]+\\.*/*[0-9]*)([a-zA-Z]*)");
            let measurementAmount = measurement[1];
            let unit = measurement[2].toUpperCase();
            if ((unit !== "" && !VALID_UNITS.includes(unit)) || measurementAmount == "") {
                setLocalError("Invalid unit passed, please correct: " + unit)
                return;
            }
            addNewItem(amountValue, nameValue);
            name.value = "";
            amount.value = "";
            amount?.focus();

        }
        setSimilarItems(undefined);
    }

    function udpateItems() {
        if (localError) {
            return;
        }
        itemService.updateItems(list!, updateList);
    }

    function updateItemString(item: Item, evt: React.FocusEvent<HTMLInputElement, Element>) {
        if (!list || localError) {
            return;
        }
        const newList = list.map((item1) => {
            if (item.id === item1.id) {
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
        if (value === undefined || localError) {
            return <></>
        }
        return <SimilarItemsPopUp items={similarItems!} addNewItem={addNewItem} value={value} updateExistingItem={updateItem} />
    }

    function clearError() {
        setLocalError(undefined);
    }

    function createError() {
        return (
            localError ?
                <div id="errorHolder">
                    < button onClick={clearError} > X</button >
                    <h2>{localError}</h2>
                </div > : <></>
        )
    }

    if (!list) {
        refreshItems();
    }

    return (
        <div style={{ height: "90px" }}>
            {createError()}
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
                                <input type="text" placeholder="Item Amount" autoComplete="off"
                                    className="todoItem" id="newTodoItemAmount"
                                    autoFocus onKeyDown={getSimilarItems} />
                                <input type="text" placeholder="Item Name" autoComplete="off"
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