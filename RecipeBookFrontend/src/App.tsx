import './index.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { ShoppingList } from './shopping/ShoppingModule';
import { RecipeForm, RecipePage, RecipesPage } from './recipe/RecipeModule';
import { CategoriesPage } from './recipe/category/CategoryModule';

const App = () => (
  <div className="App">
    <BrowserRouter>
      <Routes>
        <Route path="" element={<RecipesPage />} />
        <Route path="/recipes" element={<RecipesPage />} />
        <Route path="/recipes/category=:category" element={<RecipesPage />} />
        <Route path="/recipes/chosen" element={<RecipesPage chosen={true}/>} />
        <Route path="recipes/:id" element={<RecipePage />} />
        <Route path="/recipe/new" element={<RecipeForm />} />
        <Route path="/recipe/:id/edit" element={<RecipeForm />} />
        <Route path="/categories" element={<CategoriesPage />} />
        <Route path="/shoppinglist" element={<ShoppingList />} />
      </Routes>
    </BrowserRouter>
  </div>
)
export default App;