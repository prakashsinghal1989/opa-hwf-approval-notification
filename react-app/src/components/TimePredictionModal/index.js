import { bool, func } from 'prop-types';

// Components
import GenericModal from '../GenericModal';
import Button from '../Button';
// Styles
import './styles.scss';

const TimePredictionModal = ({ handleModalClose, timePredictionModalStatus }) => {
	return (
		<GenericModal
			modalStatus={timePredictionModalStatus}
			handleClose={handleModalClose}
			closeIconLabel="Close Modal"
			additionalClasses="d-flex flex-column align-items-center opa-time-prediction__modal"
		>
			<h2 className="opa-time-prediction__modal-title">Time Prediction</h2>
			<p className="opa-time-prediction__modal-description">
				Your Expense is predicted to be Approved within 3 days!
			</p>
			<Button onClick={handleModalClose} additionalClasses="opa-time-prediction__modal-btn">
				Close
			</Button>
		</GenericModal>
	);
};

TimePredictionModal.propTypes = {
	handleModalClose: func.isRequired,
	timePredictionModalStatus: bool,
};

TimePredictionModal.defaultProps = {
	timePredictionModalStatus: false,
};

export default TimePredictionModal;
