export type NutritionalInfo = {
    uri: string;
    calories: number;
    totalWeight: number;
    dietLabels: string[];
    healthLabels: string[];
    cautions: string[];
}

type NutritionalInfoProps = {
    itemName: string;
    nutritionalInfo: NutritionalInfo;
}

export const NutritionalInfoElement = ({ itemName, nutritionalInfo }: NutritionalInfoProps) => {
    return <table id="nutrition">
        <thead>
            <tr>
                <td colSpan={3}>
                    <h4 style={{ textAlign: "center" }}>{"Nutrtional info for: " + itemName}</h4>
                </td>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <h4>{"Calories: " + nutritionalInfo.calories}</h4>
                </td>
                <td colSpan={2}>
                    <h4>{"Weight: " + nutritionalInfo.totalWeight}</h4>
                </td>
            </tr>
            <tr>
                <td> <h4>Diet Labels:</h4>
                    {nutritionalInfo.dietLabels.map((dL, i) => <span>{i === nutritionalInfo.dietLabels.length - 1 ? dL : dL + ", "}</span>)}</td>
                <td>
                    <h4>Health Labels</h4>
                    {nutritionalInfo.healthLabels.map((hL, i) => <span>{i === nutritionalInfo.healthLabels.length - 1 ? hL : hL + ", "}</span>)}
                </td>
                <td>
                    <h4>Cuations</h4>
                    {nutritionalInfo.cautions.map((caution, i) => <span>{i === nutritionalInfo.cautions.length - 1 ? caution : caution + ", "}</span>)}
                </td>
            </tr>
        </tbody>
    </table>
}