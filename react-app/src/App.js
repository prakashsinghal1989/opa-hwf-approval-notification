import { BrowserRouter } from 'react-router-dom';
// Providers
import { AppProvider } from './contexts/AppContext';

// Components
import PageLayout from './components/PageLayout';

// Routes
import Routes from './routes';

// Styles
import './App.scss';

function App() {
	return (
		<BrowserRouter>
			<AppProvider>
				<div className="opa-app w-100">
					<PageLayout>
						<Routes />
					</PageLayout>
				</div>
			</AppProvider>
		</BrowserRouter>
	);
}

export default App;
