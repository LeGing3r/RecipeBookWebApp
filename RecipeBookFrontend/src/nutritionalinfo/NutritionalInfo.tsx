export type NutritionalInfo = {
    calories: number;
    nutrients: string[];
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
    return <div>
        <h6>{"Nutrtional info for: " + itemName}</h6>
        <h6>{"Calories: " + nutritionalInfo.calories}</h6>
        <h6>{"Weight: " + nutritionalInfo.totalWeight}</h6>
        <h6>Nutrients:</h6>
        {nutritionalInfo.nutrients.map((nutrient, i) => <span>{i === nutritionalInfo.nutrients.length - 1 ? nutrient : nutrient + ", "}</span>)}
        <h6>Diet Labels:</h6>
        {nutritionalInfo.dietLabels.map((dL, i) => <span>{i === nutritionalInfo.dietLabels.length - 1 ? dL : dL + ", "}</span>)}
        <h6>Health Labels</h6>
        {nutritionalInfo.healthLabels.map((hL, i) => <span>{i === nutritionalInfo.healthLabels.length - 1 ? hL : hL + ", "}</span>)}
        <h6>Cuations</h6>
        {nutritionalInfo.cautions.map((caution, i) => <span>{i === nutritionalInfo.cautions.length - 1 ? caution : caution + ", "}</span>)}
    </div>
}