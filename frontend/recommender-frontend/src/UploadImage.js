import React, { useState } from 'react';
import axios from 'axios';
import { Box, Button, CircularProgress, Typography, List, ListItem } from '@mui/material';

const UploadImage = () => {
    const [file, setFile] = useState(null);
    const [loading, setLoading] = useState(false);
    const [ingredients, setIngredients] = useState([]);
    const [error, setError] = useState('');

    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleUpload = async () => {
        if (!file) {
            setError('No file selected to upload.');
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
            setError('An error occurred while uploading the file.');
            setLoading(false);
        }
    };

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', mt: 4 }}>
            <Typography variant="h4" component="h1" gutterBottom>
                Upload Image
            </Typography>
            <input
                accept="image/*"
                capture="environment"
                style={{ display: 'none' }}
                id="raised-button-file"
                type="file"
                onChange={handleFileChange}
            />
            <label htmlFor="raised-button-file">
                <Button variant="contained" component="span">
                    Choose File
                </Button>
            </label>
            {file && (
                <Typography variant="body1" component="p" sx={{ mt: 2 }}>
                    {file.name}
                </Typography>
            )}
            <Button
                variant="contained"
                color="primary"
                onClick={handleUpload}
                sx={{ mt: 2 }}
                disabled={loading}
            >
                Upload
            </Button>
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
        </Box>
    );
};

export default UploadImage;
