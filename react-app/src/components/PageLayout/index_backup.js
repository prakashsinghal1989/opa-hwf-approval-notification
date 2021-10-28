/**
 * Page Layout Component which renders the entire page
 */
import { string, node } from 'prop-types';
//  import { Link, useHistory } from 'react-router-dom';
import { Link } from 'react-router-dom';
import { useContext, useState } from 'react';

// Context
import { AppContext } from '../../contexts/AppContext';

// Config
//  import userNameConfig from '../../config/userNameConfig';
// Components
import Loader from '../Loader';
import Button from '../Button';

// Logo Assets
import LogoImage from '../../assets/images/logo.png';

// Styles
import './styles.scss';

const PageLayout = ({ additonalClasses, children }) => {
	const [loaderVisible, , selectedUser] = useContext(AppContext);
	//  const [loaderVisible, , selectedUser, setSelectedUser] = useContext(AppContext);
	const [showUserDropDown, setUserDropDownStatus] = useState(false);
	//  const history = useHistory();
	/**
	 * Method to toggle the user DropDown
	 */
	const handleUserDropdownToggle = () => {
		setUserDropDownStatus(!showUserDropDown);
	};

	// /**
	//  * Method to set the selected User
	//  * @param {*} name
	//  */
	// const handleUserOptionClick = (name) => {
	// 	setSelectedUser(name);
	// 	handleUserDropdownToggle();
	// 	history.push({
	// 		pathname: `/expense-landing`,
	// 	});
	// };

	/**
	 * Method to get the first Letter of the selected User.
	 * @returns String
	 */
	const getUserInitial = () => {
		return selectedUser.substring(0, 1);
	};

	return (
		<div className={`opa-page-layout ${additonalClasses}`}>
			<header className="opa-page-layout__header d-flex align-items-center justify-content-between">
				<Link
					to={{
						pathname: '/',
					}}
					className="d-flex flex-column justify-content-center align-items-center opa-grid-cell--link "
				>
					<img
						className="opa-page-layout__header--logo"
						src={LogoImage}
						alt="Logo"
						data-testid="logo-img"
					/>
				</Link>
				{selectedUser && (
					<nav className="opa-page-layout__header-nav d-flex align-items-center mr-3">
						<div className="mr-4 opa-page-layout__header-nav--user-name">{selectedUser}</div>
						<Button
							type="button"
							onClick={handleUserDropdownToggle}
							additionalClasses="opa-page-layout__header-nav--user-btn mr-2"
						>
							<span>{getUserInitial()}</span>
						</Button>
						{/* <Button type="button" onClick={handleUserDropdownToggle} onlyIcon>
							<i className="fas fa-angle-down opa-page-layout__header-nav--user-btn-icon" />
						</Button>
						{showUserDropDown && (
							<div className="opa-page-layout__header-nav--user-dropdown">
								<ul role="menu">
									{userNameConfig.map((userName) => (
										<li key={`option-${userName}`} className="d-flex align-items-center">
											<button
												type="button"
												role="menuitem"
												className="opa-page-layout__header-nav--user-dropdown--list-item w-100 text-left"
												onClick={() => {
													handleUserOptionClick(userName);
												}}
											>
												{userName}
											</button>
										</li>
									))}
								</ul>
							</div>
						)} */}
					</nav>
				)}
			</header>
			<main className="opa-page-layout__content">{children}</main>
			{loaderVisible && <Loader />}
		</div>
	);
};

PageLayout.propTypes = {
	additonalClasses: string,
	children: node,
};

PageLayout.defaultProps = {
	additonalClasses: '',
	children: null,
};

export default PageLayout;
