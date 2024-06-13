import * as React from 'react';
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton, { IconButtonProps } from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import Link from "@mui/material/Link";

interface ExpandMoreProps extends IconButtonProps {
    expand: boolean;
}

const sectionTitleStyle = {
    pt: 30,
    pb: 12,
    color: '#133337',
};

const sourceLinkStyle = {
    color: '#1976d2',
    textDecoration: 'none',
};

const ExpandMore = styled((props: ExpandMoreProps) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
})(({ theme, expand }) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
}));

export default function RecipeReviewCard(props) {
    const recipe = props.recipe

    const [expanded, setExpanded] = React.useState(false);

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    // takes a string, remove html formating, and return the string concatenated to maxLength
    function removeTagsAndConcatenate(str) {
        let maxLength = 300

        // Regular expression to identify HTML tags in
        // the input string. Replacing the identified
        // HTML tag with a null string.
        let strWithHtmlRemoved
        strWithHtmlRemoved = str.replace(/(<([^>]+)>)/ig, '')

        if (strWithHtmlRemoved.length > maxLength) {
            strWithHtmlRemoved = strWithHtmlRemoved.substring(0, maxLength) + '...';
        }

        return strWithHtmlRemoved

    }

    // converts ["apple", "banana"] to "apple, banana"
    function formatIngredients(ingredients) {
        let ingredientNames = ingredients.map(ingredient => ingredient.name)
        return ingredientNames.join(", ")
    }

    return (
        <Card sx={{ width: '100%' , height: '100%'}}>
            <CardHeader
                sx={{ height: 150, }}
                action={
                    <IconButton aria-label="settings">
                        <MoreVertIcon />
                    </IconButton>
                }
                title={recipe.title}
                subheader={`Recipe From ${recipe.sourceName}`}
            />
            <CardMedia
                component="img"
                height="300"
                image={recipe.image}
            />
            <CardContent>
                <Typography variant="body2" color="text.secondary">
                    {removeTagsAndConcatenate(recipe.summary)}
                </Typography>
            </CardContent>
            <CardActions disableSpacing>
                <ExpandMore
                    expand={expanded}
                    onClick={handleExpandClick}
                    aria-expanded={expanded}
                    aria-label="show more"
                >
                    <ExpandMoreIcon />
                </ExpandMore>
            </CardActions>
            <Collapse in={expanded} timeout="auto" unmountOnExit>
                <CardContent>
                    <Typography variant="h6" style={sectionTitleStyle}>
                        Recipe Ingredients
                    </Typography>
                    <Typography paragraph>
                        {formatIngredients(recipe.extendedIngredients)}
                    </Typography>

                    <Typography variant="h6" style={sectionTitleStyle}>
                        Summary
                    </Typography>
                    <Typography paragraph>
                        <div dangerouslySetInnerHTML={{ __html: recipe.summary }} />
                    </Typography>

                    <Typography variant="h6" style={sectionTitleStyle}>
                        Source
                    </Typography>
                    <Typography>
                        <Link href={recipe.sourceUrl} target="_blank" rel="noopener noreferrer" style={sourceLinkStyle}>
                            {recipe.sourceUrl}
                        </Link>
                    </Typography>
                </CardContent>
            </Collapse>
        </Card>
    );
}
