/**
 * Create Expense Report Page
 */
import { useState, useContext } from 'react';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';
import clsx from 'clsx';

// Configs
import expenseReportTypeConfig from '../../../config/expenseReportTypeConfig';
import expenseCurrencyConfig from '../../../config/expenseCurrencyConfig';
import urlConfig from '../../../config/urlConfig';

// Network
import NetworkUtils from '../../../network';

// ValidationSchema
import validationSchema from './validationSchema';

// Context
import { AppContext } from '../../../contexts/AppContext';

// Utils
import { getAttachMentFlag, getSubmitExpensePayload } from '../../../utils/ExpenseUtils';

// Components
import TextInput from '../../../components/TextInput';
import Button from '../../../components/Button';
import Dropdown from '../../../components/Dropdown';
import CheckBox from '../../../components/CheckBox';
import TimePredictionModal from '../../../components/TimePredictionModal';
import TextArea from '../../../components/TextArea';

// Styles
import './styles.scss';

const { timePredictionUrl, expenseSubmitUrl } = urlConfig;

const CreateReport = () => {
	const [showOtherFields, setShowOtherFields] = useState(false);
	const [timePredictionCompleted, setTimePredictionFlag] = useState(false);
	const [timePredictionModalStatus, setTimePredictionModalStatus] = useState(false);
	const [timePredictionMessage, setTimePredictionMessage] = useState('');
	const [errorMessage, setErrorMessage] = useState('');
	const [, setLoaderVisible] = useContext(AppContext);

	/**
	 * getTimePrediction makes an api call to time prediction api, in order to get the no
	 * of days in which the expense being raised will be Approved. on Sucess of api call ,
	 * it shows the time prediction Modal
	 * @param {*} values
	 */
	const getTimePrediction = async (values) => {
		try {
			setLoaderVisible(true);
			setErrorMessage('');
			const { type, amount, isReceiptMissing, selectedFile } = values;

			const payload = {
				processName: 'Expense',
				category: type,
				amount,
				priority: Math.floor(Math.random() * (3 - 1 + 1) + 1),
				hasAttachment: getAttachMentFlag({ isReceiptMissing, selectedFile }),
			};
			const result = await NetworkUtils.makeApiRequest({
				url: timePredictionUrl,
				method: 'post',
				data: payload,
			});

			const { responseData } = result;
			if (responseData) {
				const predictionMessage = `Your Expense is predicted to be Approved within ${responseData} days!`;
				setTimePredictionMessage(predictionMessage);
				setTimePredictionModalStatus(true);
			}
		} catch (e) {
			setErrorMessage(e.errorMessage);
		} finally {
			setLoaderVisible(false);
		}
	};

	/**
	 * Method to close the Modal
	 */
	const handleModalClose = () => {
		setTimePredictionModalStatus(false);
		setTimePredictionFlag(true);
	};

	/**
	 * getSelectedFileDetails : shows the file details of the file chosen by a user to be
	 * uploaded.
	 * @param {*}
	 * @returns Html Node
	 */
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

	/**
	 * getConfirmationCTA: based on wether the time prediction was completed or not , it
	 * shows either the next or submit CTA
	 * Next button will call the time prediction api and submit CTA will call the expense
	 * start service
	 * @param {*} param0
	 * @returns
	 */
	const getConfirmationCTA = ({ isSubmitting, isValid, values }) => {
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
				onClick={() => {
					getTimePrediction(values);
				}}
				key="next-btn"
			>
				Next
			</Button>
		);
	};

	/**
	 * submitReport: based on form submission and values constructs the payload and makes
	 * an api call to start the expense process
	 * @param {*} values
	 */
	const submitReport = async (values) => {
		try {
			setErrorMessage('');
			const payload = getSubmitExpensePayload(values);
			console.log('expense submit payload:', payload);
			const result = await NetworkUtils.makeApiRequest({
				url: expenseSubmitUrl,
				method: 'post',
				data: payload,
			});

			const { responseData } = result;
			if (responseData) {
				console.log(responseData);
			}
		} catch (e) {
			setErrorMessage(e.errorMessage);
		} finally {
			setLoaderVisible(false);
		}
	};

	return (
		<div className="opa-create-report">
			<h1 className="opa-create-report--title">Create Expense Report</h1>
			<Formik
				initialValues={{
					purpose: '',
					recieptDate: '',
					type: '',
					amount: '',
					currency: '',
					selectedFile: null,
					isReceiptMissing: false,
					description: '',
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
					values,
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
									name="recieptDate"
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
								<TextArea name="description" labelKey="Description" id="description" />
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
								{getConfirmationCTA({ isSubmitting, isValid, values })}
							</div>
						)}
					</Form>
				)}
			</Formik>
			{errorMessage && <p className="opa-error-text">{errorMessage}</p>}
			{timePredictionModalStatus && (
				<TimePredictionModal
					handleModalClose={handleModalClose}
					timePredictionModalStatus={timePredictionModalStatus}
					timePredictionMessage={timePredictionMessage}
				/>
			)}
		</div>
	);
};

export default CreateReport;
