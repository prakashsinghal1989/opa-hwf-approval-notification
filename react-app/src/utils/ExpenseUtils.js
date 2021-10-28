/**
 * getAttachMentFlag: determines the hasAttachment flag based on the attributes passed.
 * @param {*}
 * @returns hasAttachment -- boolean
 */
export const getAttachMentFlag = ({ isReceiptMissing, selectedFile }) => {
	let hasAttachment = 'FALSE';
	if (selectedFile && !isReceiptMissing) {
		hasAttachment = 'TRUE';
	}
	return hasAttachment;
};

/**
 * getSubmitExpensePayload : constructs the expense payload and returns the same
 * @param {*} values
 * @returns payload -- {}
 */
export const getSubmitExpensePayload = ({ values, selectedUser }) => {
	const {
		amount,
		purpose,
		isReceiptMissing,
		selectedFile,
		type,
		recieptDate,
		currency,
		description,
	} = values;
	const payload = {
		variables: {
			// taskUrl: {
			// 	value: 'http://localhost:3000',
			// 	type: 'String',
			// },
			processName: {
				value: 'Expense',
				type: 'String',
			},
			invoiceAmount: {
				value: amount,
				type: 'String',
			},
			invoiceTitle: {
				value: purpose,
				type: 'String',
			},
			invoiceDate: {
				value: recieptDate,
				type: 'String',
			},
			currency: {
				value: currency,
				type: 'String',
			},
			invoiceDescription: {
				value: description,
				type: 'String',
			},
			creatorId: {
				value: selectedUser,
				type: 'String',
			},
			// creatorEmail: {
			// 	value: 'siddhant.wadhera@oracle.com',
			// 	type: 'String',
			// },
			// assigneeId: {
			// 	value: 'Vivek',
			// 	type: 'String',
			// },
			// assigneeEmail: {
			// 	value: 'vivek.ka.verma@oracle.com',
			// 	type: 'String',
			// },
			subType: {
				value: type,
				type: 'String',
			},
			hasAttachment: {
				value: getAttachMentFlag({ isReceiptMissing, selectedFile }),
				type: 'String',
			},
		},
	};
	return payload;
};
