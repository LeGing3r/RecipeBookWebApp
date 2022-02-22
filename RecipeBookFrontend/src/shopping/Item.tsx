import React, { KeyboardEventHandler } from "react"
import { NutritionalInfo, NutritionalInfoElement } from "../nutritionalinfo/NutritionalInfoModule"

export type Item = {
    name: string;
    measurement: string;
    needed: boolean;
    publicId: string;
}

type ItemPageProps = {
    name: string;
    defaultMeasurement: string;
    publicId: string;
    aliases: string[];
    nutritionalInfo: NutritionalInfo;
}

type ItemProps = {
    item: Item;
    index: number;
    updateNeeded: (item: Item) => void;
    updateItemString: (item: Item, evt: React.FocusEvent<HTMLInputElement>) => void;
}

export const ItemPageElement = (props: ItemPageProps) => {
    return <div>
        <h2>{props.name}</h2>
        <table>
            <th>Properties</th>
            <tbody>
                <tr>
                    {"Default measurement: " + props.defaultMeasurement}
                </tr>
                <tr>
                    <h4>Aliases:</h4>
                    {props.aliases.length === 0 ? "None" :
                        props.aliases.map(s => <h6>{s}</h6>)}
                </tr>
                <tr>
                    <h4>Nutritional Info:</h4>
                    <NutritionalInfoElement nutritionalInfo={props.nutritionalInfo} itemName={props.name} />
                </tr>
            </tbody>
        </table>
    </div>
}

export const ItemElement = ({ item, index, updateNeeded, updateItemString }: ItemProps) => {
    var measurement = item.measurement;
    if (measurement.includes("NONE")) {
        if (measurement.includes(".0NONE")) {
            let index = item.measurement.indexOf(".0NONE")
            measurement = measurement.substring(0, index);
        } else {
            let index = item.measurement.indexOf("NONE")
            measurement = measurement.substring(0, index);
        }
    }
    return (
        <li className="item" key={item.publicId}>
            <input type="checkbox" className="dblchkbx" id={item.name + item.publicId}
                checked={item.needed} onChange={() => updateNeeded(item)} />
            <input className="item-text" type="text" defaultValue={measurement + " " + item.name} id={"itemNr" + index}
                style={{ textDecoration: item.needed ? 'none' : 'line-through' }} onBlur={(evt) => updateItemString(item, evt)} />
        </li>
    )
}

export const SimilarItemsPopUp = (props: { items: Item[], addNewItem: (value: string) => void, updateExistingItem: (value: string, existing: Item) => void, value: string }) => {
    return (
        <div>
            <h2>{"Are any of these items the same as " + props.value}</h2>
            <ul>
                {props.items.map(item => {
                    return (
                        <li><a onClick={() => props.updateExistingItem(props.value, item)}>{item.name}</a></li>
                    )
                })}
                <li><a onClick={() => props.addNewItem(props.value)}>Add as new item</a></li>
            </ul>
        </div >
    )
}

