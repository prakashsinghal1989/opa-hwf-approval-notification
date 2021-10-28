/**
 * Overall App Context
 */
import { useState, createContext } from 'react';
import { node } from 'prop-types';

const AppContext = createContext();

const AppProvider = ({ children }) => {
	const [loaderVisible, setLoaderVisible] = useState(false);
	const [selectedUser, setSelectedUser] = useState('Atul');

	return (
		<AppContext.Provider value={[loaderVisible, setLoaderVisible, selectedUser, setSelectedUser]}>
			{children}
		</AppContext.Provider>
	);
};

AppProvider.propTypes = {
	children: node.isRequired,
};

export { AppContext, AppProvider };
