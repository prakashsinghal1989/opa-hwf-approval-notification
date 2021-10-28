/**
 * Time Prediction Modal Component
 */
import { bool, func, string } from 'prop-types';

// Components
import GenericModal from '../GenericModal';
import Button from '../Button';

// Styles
import './styles.scss';

const ExpenseModal = ({ handleModalClose, modalStatus, userMessage, modalTitle }) => {
	return (
		<GenericModal
			modalStatus={modalStatus}
			handleClose={handleModalClose}
			closeIconLabel="Close Modal"
			additionalClasses="d-flex flex-column align-items-center opa-expense__modal"
		>
			<h2 className="opa-expense__modal-title">{modalTitle}</h2>
			<p className="opa-expense__modal-description">{userMessage}</p>
			<Button onClick={handleModalClose} additionalClasses="opa-expense__modal-btn">
				Close
			</Button>
		</GenericModal>
	);
};

ExpenseModal.propTypes = {
	handleModalClose: func.isRequired,
	modalStatus: bool,
	userMessage: string,
	modalTitle: string,
};

ExpenseModal.defaultProps = {
	modalStatus: false,
	userMessage: '',
	modalTitle: '',
};

export default ExpenseModal;
