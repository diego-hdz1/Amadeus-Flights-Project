import { it, expect, describe } from 'vitest';
import {render, screen} from '@testing-library/react';
import Header from '../../src/components/header';
import * as React from 'react';
import '@testing-library/jest-dom/vitest';

describe('Header', () => {
    it('should render Amadeus flight application ', () => {
        render(<Header/>);
        const heading = screen.getByRole('heading');
        expect(heading).toBeInTheDocument();
        expect(heading).toHaveTextContent(/Amadeus Flight App/i);
    });
})