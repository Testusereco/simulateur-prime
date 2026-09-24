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
    test('accès autorisé et navigation vers /simulation', async ({ page }) => {
    await page.goto('/');

    await page.getByTestId('age-input').fill('25');
    await page.getByTestId('submit-button').click();

    await expect(page).toHaveURL('/simulation');
    await expect(page.getByTestId('age-input')).toBeVisible();
  });
});