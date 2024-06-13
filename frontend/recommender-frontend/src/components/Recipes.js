import {Card, CardMedia} from "@mui/material";

export default function Recipes({recipes}) {
    return (
        <>
            {recipes.map((recipe) =>
                <Card key={recipe.id}>
                    <CardMedia
                        sx={{ height: 140 }}
                        image={recipe.image}
                        title="green iguana"
                    />
                </Card>
            )}
        </>
    );
}