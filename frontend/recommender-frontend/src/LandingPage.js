import * as React from 'react';

import CssBaseline from '@mui/material/CssBaseline';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import Hero from './components/Hero';
import Footer from './components/Footer';



export default function LandingPage() {
    const [mode, setMode] = React.useState('light');
    const defaultTheme = createTheme({ palette: { mode } });

    const toggleColorMode = () => {
        setMode((prev) => (prev === 'dark' ? 'light' : 'dark'));
    };

    return (
        <ThemeProvider theme={defaultTheme}>
            <CssBaseline />
            <Hero />
        </ThemeProvider>
    );
}