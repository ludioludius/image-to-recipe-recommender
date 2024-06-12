import * as React from 'react';
import {alpha, CircularProgress, List, ListItem} from '@mui/material';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Link from '@mui/material/Link';
import Stack from '@mui/material/Stack';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import UploadImage from "../UploadImage";
import {useRef, useState} from 'react';
import axios from "axios";


export default function Hero() {
    const fileInputRef = useRef(null);

    const [ingredients, setIngredients] = useState([]);
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
            const response = await axios.post(`${process.env.REACT_APP_BACKEND_URL}/ingredients`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            setIngredients(response.data);
            setLoading(false);
        } catch (error) {
            console.log(error);
            setError('An error occurred');
            setLoading(false);
        }
    };

    return (
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
                        By clicking &quot;Start now&quot; you agree to our&nbsp;
                        <Link href="#" color="primary">
                        Terms & Conditions
                        </Link>
                        .
                    </Typography>
                </Stack>
                {loading && <CircularProgress sx={{ mt: 2 }} />}
                {error && (
                    <Typography variant="body1" color="error" sx={{ mt: 2 }}>
                        {error}
                    </Typography>
                )}
                <List sx={{ mt: 2 }}>
                    {ingredients.map((ingredient, index) => (
                        <ListItem key={index}>{ingredient}</ListItem>
                    ))}
                </List>
            </Container>
        </Box>
    );
}