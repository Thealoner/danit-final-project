import React, { Fragment } from 'react';
// import { Formik, Field, Form, ErrorMessage } from 'formik';
// import * as Yup from 'yup';

const ClientEditor = ({ data, updateUser }) => {
  return (
    <div className="clienteditor">
      <h1>Edit User</h1>
      <Formik
        enableReinitialize
        initialValues={ data }
        onSubmit={updateUser}
        // onSubmit={(values, actions) => {
        //   MyImaginaryRestApiCall(user.id, values).then(
        //     updatedUser => {
        //       actions.setSubmitting(false);
        //       updateUser(updatedUser);
        //     },
        //     error => {
        //       actions.setSubmitting(false);
        //       actions.setErrors(transformMyRestApiErrorsToAnObject(error));
        //       actions.setStatus({ msg: 'Set some arbitrary status or data' });
        //     }
        //   );
        // }}
        render={({ errors, touched, isSubmitting }) => (
          <Fragment>
            <Form>
              <Field type="email" name="email" />
              <ErrorMessage name="email" component="div" />

              <Field type="text" className="error" name="firstName" />
              <ErrorMessage name="firstName">
                {errorMessage => <div className="error">{errorMessage}</div>}
              </ErrorMessage>

              <Field type="text" name="lastName" />
              <ErrorMessage name="lastName" className="error" component="div" />

              {/* {status && status.msg && <div>{status.msg}</div>} */}
              <button type="submit" disabled={isSubmitting}>
                Submit
              </button>
            </Form>
          </Fragment>
        )}
      />
    </div>
  );
};

export const DisplayFormikState = props =>
  <div style={{ margin: '1rem 0' }}>
    <h3 style={{ fontFamily: 'monospace' }} />
    <pre
      style={{
        background: '#f6f8fa',
        fontSize: '.65rem',
        padding: '.5rem',
      }}
    >
      <strong>props</strong> ={' '}
      {JSON.stringify(props, null, 2)}
    </pre>
  </div>;

export default ClientEditor;