import { bool, func, string } from 'prop-types';

// Components
import GenericModal from '../GenericModal';
import Button from '../Button';
// Styles
import './styles.scss';

const TimePredictionModal = ({
	handleModalClose,
	timePredictionModalStatus,
	timePredictionMessage,
}) => {
	return (
		<GenericModal
			modalStatus={timePredictionModalStatus}
			handleClose={handleModalClose}
			closeIconLabel="Close Modal"
			additionalClasses="d-flex flex-column align-items-center opa-time-prediction__modal"
		>
			<h2 className="opa-time-prediction__modal-title">Time Prediction</h2>
			<p className="opa-time-prediction__modal-description">{timePredictionMessage}</p>
			<Button onClick={handleModalClose} additionalClasses="opa-time-prediction__modal-btn">
				Close
			</Button>
		</GenericModal>
	);
};

TimePredictionModal.propTypes = {
	handleModalClose: func.isRequired,
	timePredictionModalStatus: bool,
	timePredictionMessage: string,
};

TimePredictionModal.defaultProps = {
	timePredictionModalStatus: false,
	timePredictionMessage: '',
};

export default TimePredictionModal;
