import axios from "axios";
import { Item } from "./Item";
import { ItemUrl as itemUrl, ShoppingUrl as shoppingUrl } from "./ShoppingModule";

type ItemProps = {
    item: Item,
    updateList: (items: Item[]) => void
}

export function getItems(updateList: (items: Item[]) => void) {
    axios.get<Item[]>(shoppingUrl)
        .then(res => updateList(res.data));
}

export function createNewItem(props: ItemProps) {
    axios.post(shoppingUrl, props.item)
        .then(res => props.updateList(res.data));
}

export function updateExistingItem(props: ItemProps, id: string) {
    axios.put(itemUrl + "?itemId=" + id, props.item)
        .then(res => props.updateList(res.data))
}

export function updateItems(itemList: Item[], updateList: (items: Item[]) => void) {
    axios.put<Item[]>(shoppingUrl, itemList)
        .then(res => updateList(res.data));;
}

export async function checkForSimilarItems(name: string, updateList: (items: Item[]) => void) {
    axios.get<Item[]>(itemUrl + "?itemName=" + name)
        .then(res => updateList(res.data));
}

