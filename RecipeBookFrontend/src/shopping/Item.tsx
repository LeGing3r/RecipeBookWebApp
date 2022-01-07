import React from "react"
import { NutritionalInfo, NutritionalInfoElement } from "../nutritionalinfo/NutritionalInfoModule"

export type Item = {
    name: string;
    amount: number;
    measurement: string;
    needed: boolean;
    defaultMeasurement: string;
    publicId: string;
    stringValue: string;
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
    updateItemString: (item: Item, evt:React.FocusEvent<HTMLInputElement>) => void;
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
    var measurement = item.measurement === "NONE" ? ' ' : ' ' + item.measurement;
    if (!item.stringValue) {
        item.stringValue = item.amount + measurement + ' ' + item.name;
    }
    return (
        <li className="item" key={item.name}>
            <input type="hidden" key={'' + index} />
            <input type="checkbox" className="dblchkbx" id={index + item.name}
                checked={item.needed} onChange={() => updateNeeded(item)} />
            <input className="item-text" type="text" defaultValue={item.stringValue}
                style={{ textDecoration: item.needed ? 'none' : 'line-through' }} onBlur={(evt) => updateItemString(item, evt)} />
        </li>
    )
}

