import React, { useState } from 'react';

const RegisterPage = () => {
  const [formData, setFormData] = useState({
    fullname: '',
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validateForm(formData);
    if (Object.keys(validationErrors).length === 0) {
      // Form is valid, submit data
      console.log('Form submitted:', formData);
    } else {
      // Form has errors, display them
      setErrors(validationErrors);
    }
  };

  const validateForm = (data) => {
    const errors = {};

    // Validate fullname
    if (!data.fullname.trim()) {
      errors.fullname = 'Fullname is required';
    }

    // Validate username
    if (!data.username.trim()) {
      errors.username = 'Username is required';
    }

    // Validate email
    if (!data.email.trim()) {
      errors.email = 'Email is required';
    } else if (!isValidEmail(data.email)) {
      errors.email = 'Invalid email address';
    }

    // Validate password
    if (!data.password.trim()) {
      errors.password = 'Password is required';
    } else if (!isValidPassword(data.password)) {
      errors.password = 'Password must be at least 8 characters long with a combination of upper and lower case letters, special characters, and numbers';
    }

    // Validate confirmPassword
    if (data.password !== data.confirmPassword) {
      errors.confirmPassword = 'Passwords do not match';
    }

    return errors;
  };

  const isValidEmail = (email) => {
    // Regular expression for validating email address
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const isValidPassword = (password) => {
    // Regular expression for validating password
    const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[a-zA-Z]).{8,}$/;
    return passwordRegex.test(password);
  };

  return (
    <div>
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Fullname</label>
          <input type="text" name="fullname" value={formData.fullname} onChange={handleChange} />
          {errors.fullname && <span className="error">{errors.fullname}</span>}
        </div>
        <div>
          <label>Username</label>
          <input type="text" name="username" value={formData.username} onChange={handleChange} />
          {errors.username && <span className="error">{errors.username}</span>}
        </div>
        <div>
          <label>Email</label>
          <input type="email" name="email" value={formData.email} onChange={handleChange} />
          {errors.email && <span className="error">{errors.email}</span>}
        </div>
        <div>
          <label>Password</label>
          <input type="password" name="password" value={formData.password} onChange={handleChange} />
          {errors.password && <span className="error">{errors.password}</span>}
        </div>
        <div>
          <label>Confirm Password</label>
          <input type="password" name="confirmPassword" value={formData.confirmPassword} onChange={handleChange} />
          {errors.confirmPassword && <span className="error">{errors.confirmPassword}</span>}
        </div>
        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default RegisterPage;
