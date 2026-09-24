import { test, expect } from '@playwright/test';

test.describe('Simulateur de prime', () => {
  test('accès refusé pour un citoyen trop jeune', async ({ page }) => {
    await page.goto('/');

    await page.getByTestId('age-input').fill('17');
    await page.getByTestId('submit-button').click();

    await expect(page.getByTestId('error-message')).toHaveText(
      'Citoyen inéligible : âge hors limites',
    );
    await expect(page).toHaveURL('/');
  });
});