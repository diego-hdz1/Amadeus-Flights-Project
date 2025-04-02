import { it, expect, describe } from 'vitest';
import {fireEvent, render, screen} from '@testing-library/react';
import Error from '../../src/components/error';
import * as React from 'react';
import '@testing-library/jest-dom/vitest';
import {vi} from 'vitest';
import {MemoryRouter, BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";


describe('Error', () => {
    it('should render correctly the error', () => {
        render(
            <MemoryRouter>
                <Error/>
            </MemoryRouter>
        );

        expect(screen.getByText("Submission Failed")).toBeInTheDocument();
        expect(screen.getByText("There are no flights for the input search parameters")).toBeInTheDocument();
        expect(screen.getByText("Return to search")).toBeInTheDocument();
    })

})