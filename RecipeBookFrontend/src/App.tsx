import './index.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { ShoppingList } from './shopping/ShoppingModule';
import { RecipeForm, RecipePage, RecipesPage } from './recipe/RecipeModule';
import { CategoriesForm, CategoriesPage, CategoryEdit } from './category/CategoryModule';

const App = () => (
  <div className="App">
    <BrowserRouter>
      <Routes>
        <Route path="" element={<RecipesPage />} />
        <Route path="/recipes" element={<RecipesPage />} />
        <Route path="recipes/:id" element={<RecipePage />} />
        <Route path="/recipes/new" element={<RecipeForm />} />
        <Route path="/recipes/:id/edit" element={<RecipeForm />} />
        <Route path="/categories" element={<CategoriesPage />} />
        <Route path="/categories/new" element={<CategoriesForm />} />
        <Route path="/categories/:id/edit" element={<CategoryEdit />} />
        <Route path="/shoppinglist" element={<ShoppingList />} />
      </Routes>
    </BrowserRouter>
  </div>
)
export default App;