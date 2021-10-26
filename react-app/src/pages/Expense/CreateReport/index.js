import { useState } from 'react';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';
// Configs
import expenseReportTypeConfig from '../../../config/expenseReportTypeConfig';
import expenseCurrencyConfig from '../../../config/expenseCurrencyConfig';

// ValidationSchema
import validationSchema from './validationSchema';
// Components
import TextInput from '../../../components/TextInput';
import Button from '../../../components/Button';
import Dropdown from '../../../components/Dropdown';

// Styles
import './styles.scss';

const CreateReport = () => {
	const [showOtherFields, setShowOtherFields] = useState(false);
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
				{({ values, errors = {}, isSubmitting, isValid, handleReset }) => (
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
								disabled={!values.purpose || errors.purpose}
							>
								Create Item
							</Button>
						)}
						{showOtherFields && (
							<div className="d-flex opa-create-report__form--action-btns">
								<Button
									type="button"
									disabled={isSubmitting}
									additionalClasses="opa-create-report__form--btn mr-5"
									onClick={() => {
										setShowOtherFields(false);
										handleReset();
									}}
								>
									Cancel
								</Button>
								<Button
									type="submit"
									disabled={isSubmitting || !isValid}
									additionalClasses="opa-create-report__form--btn"
								>
									Save
								</Button>
							</div>
						)}
					</Form>
				)}
			</Formik>
		</div>
	);
};

export default CreateReport;
