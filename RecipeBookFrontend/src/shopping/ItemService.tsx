import axios from "axios";
import { Item } from "./Item";
import { ShoppingUrl } from "./ShoppingModule";

export function getItems(updateList: (items: Item[]) => void) {
    axios.get<Item[]>(ShoppingUrl)
        .then(res => updateList(res.data));
}

export function addNewItem(value: string) {
    axios.post<string>(ShoppingUrl, value);
}
export function updateItems(itemList: Item[], updateList: (items: Item[]) => void) {
    axios.put<Item[]>(ShoppingUrl, itemList)
        .then(res => updateList(res.data));;
}

