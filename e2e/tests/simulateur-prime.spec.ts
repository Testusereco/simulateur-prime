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
    test('calcul et affichage du montant quand éligible', async ({ page }) => {
    await page.goto('/');
    await page.getByTestId('age-input').fill('25');
    await page.getByTestId('submit-button').click();

    await page.getByTestId('age-input').fill('25');
    await page.getByTestId('a-input').fill('1000');
    await page.getByTestId('b-input').fill('1000');
    await page.getByTestId('c-input').fill('0');
    await page.getByTestId('d-input').fill('0');
    await page.getByTestId('e-input').fill('1');
    await page.getByTestId('f-input').fill('1');
    await page.getByTestId('g-input').fill('1');
    await page.getByTestId('h-input').fill('1');
    await page.getByTestId('submit-button').click();

    await expect(page.getByTestId('statut')).toContainText('éligible');
    await expect(page.getByTestId('montant')).toContainText('2000 €');
  });
});