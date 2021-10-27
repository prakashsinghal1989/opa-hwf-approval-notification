import { useState, useContext } from 'react';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';
import clsx from 'clsx';

// Configs
import expenseReportTypeConfig from '../../../config/expenseReportTypeConfig';
import expenseCurrencyConfig from '../../../config/expenseCurrencyConfig';

// ValidationSchema
import validationSchema from './validationSchema';

// Context
import { AppContext } from '../../../contexts/AppContext';

// Components
import TextInput from '../../../components/TextInput';
import Button from '../../../components/Button';
import Dropdown from '../../../components/Dropdown';
import CheckBox from '../../../components/CheckBox';
import TimePredictionModal from '../../../components/TimePredictionModal';

// Styles
import './styles.scss';

const CreateReport = () => {
	const [showOtherFields, setShowOtherFields] = useState(false);
	const [timePredictionCompleted, setTimePredictionFlag] = useState(false);
	const [timePredictionModalStatus, setTimePredictionModalStatus] = useState(false);
	const [, setLoaderVisible] = useContext(AppContext);
	const getTimePrediction = () => {
		setLoaderVisible(true);

		setTimeout(() => {
			setLoaderVisible(false);
			setTimePredictionModalStatus(true);
		}, 2000);
	};

	/**
	 * Method to close the Modal
	 */
	const handleModalClose = () => {
		setTimePredictionModalStatus(false);
		setTimePredictionFlag(true);
	};

	const getSelectedFileDetails = ({ selectedFile, isReceiptMissing }) => {
		if (selectedFile && !isReceiptMissing) {
			const { name, type } = selectedFile;
			return (
				<div className="opa-create-report__form--file-details">
					<p>
						<strong>File Name:</strong> {name}
					</p>
					<p>
						<strong>File Type:</strong> {type}
					</p>
				</div>
			);
		}
		return null;
	};

	const getConfirmationCTA = ({ isSubmitting, isValid }) => {
		if (timePredictionCompleted) {
			return (
				<Button
					type="submit"
					disabled={isSubmitting || !isValid}
					additionalClasses="opa-create-report__form--btn"
					key="save-btn"
				>
					Submit
				</Button>
			);
		}
		return (
			<Button
				type="button"
				disabled={!isValid}
				additionalClasses="opa-create-report__form--btn"
				onClick={getTimePrediction}
				key="next-btn"
			>
				Next
			</Button>
		);
	};
	const submitReport = (values) => {
		console.log(values);
	};

	return (
		<div className="opa-create-report">
			<h1 className="opa-create-report--title">Create Expense Report</h1>
			<Formik
				initialValues={{
					purpose: '',
					date: '',
					type: '',
					amount: '',
					currency: '',
					selectedFile: null,
					isReceiptMissing: false,
				}}
				validationSchema={Yup.object(validationSchema)}
				onSubmit={(values) => {
					if (showOtherFields) {
						submitReport(values);
					} else {
						setShowOtherFields(true);
					}
				}}
			>
				{({
					values: { purpose, isReceiptMissing, selectedFile },
					errors = {},
					isSubmitting,
					isValid,
					handleReset,
					setFieldValue,
				}) => (
					<Form autoComplete="off" className="opa-create-report__form">
						<TextInput
							labelKey="Purpose *"
							name="purpose"
							id="report-purpose"
							ariaLabel="Expense Report Purpose"
						/>
						{showOtherFields && (
							<>
								<TextInput
									labelKey="Date *"
									name="date"
									id="item-date"
									ariaLabel="Expense Item Date"
									type="date"
								/>
								<Dropdown
									dropDownItems={expenseReportTypeConfig}
									dropDownId="type"
									label="Type *"
									placeholderText="Select Expense Type"
									name="type"
								/>
								<div className="d-flex">
									<Dropdown
										dropDownItems={expenseCurrencyConfig}
										dropDownId="currency"
										label="Currency *"
										placeholderText="Select Currency"
										name="currency"
									/>
									<TextInput
										labelKey="Amount *"
										name="amount"
										id="item-amount"
										ariaLabel="Expense Item amount"
										type="tel"
									/>
								</div>
								<label className="opa-create-report__form__file-upload" htmlFor="file">
									<input
										type="file"
										id="file"
										aria-label="File browser example"
										className={clsx('opa-create-report__form__file-upload-input', {
											disabled: isReceiptMissing,
										})}
										onChange={(e) => {
											setFieldValue('selectedFile', e.currentTarget.files[0]);
										}}
										disabled={isReceiptMissing}
									/>
									<span
										className={clsx('opa-create-report__form__file-upload-custom', {
											disabled: isReceiptMissing,
										})}
									/>
								</label>
								{getSelectedFileDetails({ selectedFile, isReceiptMissing })}
								<CheckBox id="isReceiptMissing" name="isReceiptMissing" text="Is receipt Missing" />
							</>
						)}
						{!showOtherFields && (
							<Button
								type="button"
								additionalClasses="opa-create-report__form--item-btn"
								iconName="plus"
								onClick={() => {
									setShowOtherFields(true);
								}}
								disabled={!purpose || errors.purpose}
							>
								Create Item
							</Button>
						)}
						{showOtherFields && (
							<div className="d-flex opa-create-report__form--action-btns">
								<Button
									type="button"
									additionalClasses="opa-create-report__form--btn mr-5"
									onClick={() => {
										setShowOtherFields(false);
										handleReset();
									}}
								>
									Cancel
								</Button>
								{getConfirmationCTA({ isSubmitting, isValid })}
							</div>
						)}
					</Form>
				)}
			</Formik>
			{timePredictionModalStatus && (
				<TimePredictionModal
					handleModalClose={handleModalClose}
					timePredictionModalStatus={timePredictionModalStatus}
				/>
			)}
		</div>
	);
};

export default CreateReport;
