import axios from "axios";
import { Item } from "./Item";
import { ItemUrl as itemUrl, ShoppingUrl as shoppingUrl } from "./ShoppingModule";

type ItemProps = {
    value: string,
    updateList: (items: Item[]) => void
}

export function getItems(updateList: (items: Item[]) => void) {
    axios.get<Item[]>(shoppingUrl)
        .then(res => updateList(res.data));
}

export function createNewItem(props: ItemProps) {
    axios.post(shoppingUrl, props.value, {
        headers: { 
            'Content-Type' : 'text/plain' 
        }
    })
        .then(res => props.updateList(res.data));
}

export function updateExistingItem(props: ItemProps, existing: Item) {
    axios.put(itemUrl + "?id=" + existing.publicId, props.value)
        .then(res => props.updateList(res.data))
}

export function updateItems(itemList: Item[], updateList: (items: Item[]) => void) {
    axios.put<Item[]>(shoppingUrl, itemList)
        .then(res => updateList(res.data));;
}

export function checkForSimilarItems(props: ItemProps) {
    axios.get<Item[]>(itemUrl + "?item=" + props.value)
        .then(res => props.updateList(res.data))
}

