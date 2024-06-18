import * as React from 'react';
import {alpha, CircularProgress, Grid} from '@mui/material';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';
import {useRef, useState} from 'react';
import axios from "axios";
import RecipeReviewCard from "./RecipeReviewCard";


export default function Hero() {
    const fileInputRef = useRef(null);

    const [recipes, setRecipes] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleButtonClick = () => {
        fileInputRef.current.click();
    };

    const handleFileChange = async (event) => {
        const file = event.target.files[0];

        if (!file) {
            return;
        }

        const formData = new FormData();
        formData.append('image', file);

        try {
            setLoading(true);
            setError('');
            console.log("Backend URL:", process.env.REACT_APP_BACKEND_URL);
            const response = await axios.post(`${process.env.REACT_APP_BACKEND_URL}/recipes`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            console.log(response.data)
            setRecipes(response.data);
            setLoading(false);
        } catch (error) {
            console.log(error);
            setError('An error occurred');
            setLoading(false);
        }
    };

    return (
        <>
        <Box
            id="hero"
            sx={(theme) => ({
                width: '100%',
                backgroundImage:
                    theme.palette.mode === 'light'
                        ? 'linear-gradient(180deg, #CEE5FD, #FFF)'
                        : `linear-gradient(#02294F, ${alpha('#090E10', 0.0)})`,
                backgroundSize: '100% 20%',
                backgroundRepeat: 'no-repeat',
            })}
        >
            <Container
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    pt: { xs: 14, sm: 20 },
                    pb: { xs: 8, sm: 12 },
                }}
            >
                <Stack spacing={2} useFlexGap sx={{ width: { xs: '100%', sm: '70%' } }}>
                    <Typography
                        variant="h1"
                        sx={{
                            display: 'flex',
                            flexDirection: { xs: 'column', md: 'row' },
                            alignSelf: 'center',
                            textAlign: 'center',
                            fontSize: 'clamp(3.5rem, 10vw, 4rem)',
                        }}
                    >
                        Image to Recipe
                    </Typography>
                    <Typography
                        component="span"
                        variant="h1"
                        sx={{
                            display: 'flex',
                            flexDirection: { xs: 'column', md: 'row' },
                            alignSelf: 'center',
                            textAlign: 'center',
                            fontSize: 'clamp(3rem, 10vw, 4rem)',
                            color: (theme) =>
                                theme.palette.mode === 'light' ? 'primary.main' : 'primary.light',
                        }}
                    >
                        Recommender
                    </Typography>
                    <Typography
                        textAlign="center"
                        color="text.secondary"
                        sx={{ alignSelf: 'center', width: { sm: '100%', md: '80%' } }}
                    >
                        Have left over ingredients at home and don't know what to cook? Use this web app to
                        take a picture of your leftover ingredients and get recipe suggestions!
                    </Typography>
                    <Stack
                        direction={{xs: 'column', sm: 'row'}}
                        alignSelf="center"
                        spacing={1}
                        useFlexGap
                        sx={{pt: 2, width: {xs: 'auto', sm: 'auto'}}}
                    >
                        <Button variant="contained" color="primary" onClick={handleButtonClick}>
                            Take a Photo
                        </Button>
                        <input
                            accept="image/*"
                            capture="environment"
                            style={{display: 'none'}}
                            type="file"
                            ref={fileInputRef}
                            onChange={handleFileChange}
                        />
                    </Stack>

                    <Typography variant="caption" textAlign="center" sx={{opacity: 0.8}}>
                        Click the button above to take a photo on mobile or upload a photo on desktop.
                    </Typography>
                </Stack>
                {loading && <CircularProgress sx={{ mt: 2 }} />}
                {error && (
                    <Typography variant="body1" color="error" sx={{ mt: 2 }}>
                        {error}
                    </Typography>
                )}
            </Container>
        </Box>
            <Container sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                pb: { xs: 8, sm: 12 },
            }}>
                <Grid container spacing={2} alignItems="stretch" justifyContent="center">
                    {recipes.map((recipe, index) => (
                        <Grid item xs={12} sm={6} md={6} lg={6} key={index}>
                            <RecipeReviewCard recipe={recipe}/>
                        </Grid>
                    ))}
                </Grid>
            </Container>
        </>
    );
}