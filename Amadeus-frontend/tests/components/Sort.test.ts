import { it, expect, describe } from 'vitest';
import {fireEvent, render, screen} from '@testing-library/react';
import Sort from '../../src/components/sort';
import * as React from 'react';
import '@testing-library/jest-dom/vitest';
import {vi} from 'vitest';
import {MemoryRouter, BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";

const mockSetData = vi.fn();

const defaultProps ={
    setData: mockSetData,
}

describe('Filter', () => {
    it('should render correctly the text', () => {
        render(
            <MemoryRouter>
                <Filter {...defaultProps}/>
            </MemoryRouter>
        );

        expect(screen.getByText("Choose your options to sort")).toBeInTheDocument();
        expect(screen.getByText("Filter")).toBeInTheDocument();
        expect(screen.getByText("Return to search")).toBeInTheDocument();
    })

    it('should', () => {
        
    })
})