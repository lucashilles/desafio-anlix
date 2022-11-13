package br.com.lucashilles.domains.patient;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

@Entity
@Table(name = "sys_patient")
public class Patient extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @JsonProperty("nome")
    public String name;

    @JsonProperty("idade")
    public int age;

    @Column(unique = true, length = 14)
    public String cpf;

    public String rg;

    @JsonProperty("data_nasc")
    @Column(name = "date_of_birth", nullable = false,
            columnDefinition = "timestamp with time zone not null")
    public Date dateOfBirth;

    @JsonProperty("sexo")
    public String gender;

    @JsonProperty("signo")
    public String zodiac;

    @JsonProperty("mae")
    public String mother;

    @JsonProperty("pai")
    public String father;

    @Email
    public String email;

    @JsonProperty("senha")
    public String password;

    @JsonProperty("cep")
    @Column(name = "zip_code", length = 9)
    public String zipCode;

    @JsonProperty("endereco")
    public String address;

    @JsonProperty("numero")
    public String number;

    @JsonProperty("bairro")
    public String district;

    @JsonProperty("cidade")
    public String city;

    @JsonProperty("estado")
    @Column(length = 2)
    public String state;

    @JsonProperty("telefone_fixo")
    @Column(length = 14)
    public String phone;

    @JsonProperty("celular")
    @Column(name = "mobile_phone", length = 15)
    public String mobilePhone;

    @JsonProperty("altura")
    public String height;

    @JsonProperty("peso")
    public float weigth;

    @JsonProperty("tipo_sanguineo")
    @Column(name = "blood_type")
    public String bloodType;

    @JsonProperty("cor")
    public String color;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public float getWeigth() {
        return weigth;
    }

    public void setWeigth(float weigth) {
        this.weigth = weigth;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}